package buildsrc.convention

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.ListProperty
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.security.MessageDigest
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class Cap4kFlowExportPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (project != project.rootProject) return

        val extension = project.extensions.create(
            "cap4kFlow",
            Cap4kFlowExportExtension::class.java
        )

        extension.outputDir.convention(project.layout.projectDirectory.dir("flows"))
        extension.inputDirs.convention(
            listOf(
                project.layout.projectDirectory.dir("only-danmuku-adapter/build/cap4k-code-analysis"),
                project.layout.projectDirectory.dir("only-danmuku-application/build/cap4k-code-analysis"),
                project.layout.projectDirectory.dir("only-danmuku-domain/build/cap4k-code-analysis")
            )
        )

        val flowExport = project.tasks.register("cap4kFlowExport", Cap4kFlowExportTask::class.java) {
            group = "cap4k"
            description = "Export Cap4k processing flow graphs to the root flows directory."
            inputDirs.set(extension.inputDirs)
            outputDir.set(extension.outputDir)
        }

        val cleanTaskPaths = listOf(
            ":only-danmuku-adapter:clean",
            ":only-danmuku-application:clean",
            ":only-danmuku-domain:clean"
        )

        val compileTaskPaths = listOf(
            ":only-danmuku-adapter:compileKotlin",
            ":only-danmuku-application:compileKotlin",
            ":only-danmuku-domain:compileKotlin"
        )

        val flowClean = project.tasks.register("cap4kFlowClean") {
            group = "cap4k"
            description = "Clean Cap4k analysis outputs for flow export."
            dependsOn(cleanTaskPaths)
        }

        val flowCompile = project.tasks.register("cap4kFlowCompile") {
            group = "cap4k"
            description = "Compile modules needed for flow export."
            dependsOn(compileTaskPaths)
            mustRunAfter(flowClean)
        }

        flowExport.configure {
            dependsOn(flowCompile)
        }

        project.tasks.register("cap4kFlow") {
            group = "cap4k"
            description = "Clean, compile, and export Cap4k processing flows."
            dependsOn(flowClean, flowExport)
        }

        project.gradle.projectsEvaluated {
            cleanTaskPaths.forEach { cleanPath ->
                val cleanTask = project.tasks.findByPath(cleanPath)
                compileTaskPaths.forEach { compilePath ->
                    val compileTask = project.tasks.findByPath(compilePath)
                    if (cleanTask != null && compileTask != null) {
                        compileTask.mustRunAfter(cleanTask)
                    }
                }
            }
        }
    }
}

abstract class Cap4kFlowExportExtension @Inject constructor() {
    abstract val inputDirs: ListProperty<Directory>
    abstract val outputDir: DirectoryProperty
}

abstract class Cap4kFlowExportTask : DefaultTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val inputDirs: ListProperty<Directory>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun export() {
        val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)

        val nodesById = linkedMapOf<String, Node>()
        val edgeKeys = linkedSetOf<EdgeKey>()

        val inputDirectories = inputDirs.get().map { it.asFile.toPath() }
        inputDirectories.forEach { dir ->
            val nodesPath = dir.resolve("nodes.json")
            val relsPath = dir.resolve("rels.json")
            if (!Files.exists(nodesPath) || !Files.exists(relsPath)) {
                throw GradleException("Missing nodes/rels at $dir")
            }

            val rawNodes: List<RawNode> = mapper.readValue(nodesPath.toFile())
            rawNodes.forEach { raw ->
                val id = raw.id?.takeIf { it.isNotBlank() } ?: return@forEach
                if (!nodesById.containsKey(id)) {
                    nodesById[id] = normalizeNode(raw)
                }
            }

            val rawEdges: List<RawEdge> = mapper.readValue(relsPath.toFile())
            rawEdges.forEach { raw ->
                val fromId = raw.fromId?.takeIf { it.isNotBlank() } ?: return@forEach
                val toId = raw.toId?.takeIf { it.isNotBlank() } ?: return@forEach
                val type = raw.type?.takeIf { it.isNotBlank() } ?: return@forEach
                edgeKeys.add(EdgeKey(fromId, toId, type, raw.label))
            }
        }

        val edges = edgeKeys.map { Edge(it.fromId, it.toId, it.type, it.label) }
            .filter { ALLOWED_EDGE_TYPES.contains(it.type) }

        val adjacency = linkedMapOf<String, MutableList<Edge>>()
        edges.forEach { edge ->
            adjacency.computeIfAbsent(edge.fromId) { mutableListOf() }.add(edge)
        }

        val entryNodes = nodesById.values
            .filter { node -> ENTRY_NODE_TYPES.contains(node.type) }
            .sortedBy { it.id }

        val outDir = outputDir.get().asFile.toPath()
        if (Files.exists(outDir)) {
            outDir.toFile().deleteRecursively()
        }
        Files.createDirectories(outDir)

        val usedSlugs = mutableSetOf<String>()
        val flowIndexEntries = mutableListOf<FlowIndexEntry>()

        entryNodes.forEach { entry ->
            val entryId = entry.id
            val entryType = if (entryId == "<anonymous>") "<anonymous>" else entry.type
            val (flowNodes, flowEdges) = collectFlow(entryId, nodesById, adjacency)
            val slug = slugify(entryId, usedSlugs)
            val flowJsonPath = outDir.resolve("$slug.json")
            val flowMmdPath = outDir.resolve("$slug.mmd")

            val flowPayload = FlowPayload(
                entryId = entryId,
                entryType = entryType,
                nodeCount = flowNodes.size,
                edgeCount = flowEdges.size,
                nodes = flowNodes,
                edges = flowEdges
            )

            mapper.writerWithDefaultPrettyPrinter().writeValue(flowJsonPath.toFile(), flowPayload)
            Files.writeString(flowMmdPath, renderMermaid(flowNodes, flowEdges), StandardCharsets.UTF_8)

            flowIndexEntries.add(
                FlowIndexEntry(
                    entryId = entryId,
                    entryType = entryType,
                    nodeCount = flowNodes.size,
                    edgeCount = flowEdges.size,
                    json = flowJsonPath.fileName.toString(),
                    mermaid = flowMmdPath.fileName.toString()
                )
            )
        }

        val entryTypeCounts = linkedMapOf<String, Int>()
        entryNodes.forEach { node ->
            val entryId = node.id
            val entryType = if (entryId == "<anonymous>") "<anonymous>" else node.type
            entryTypeCounts[entryType] = (entryTypeCounts[entryType] ?: 0) + 1
        }

        val indexPayload = IndexPayload(
            generatedAt = OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS).toString(),
            inputDirs = inputDirectories.map { it.toString() },
            entryTypes = entryTypeCounts.keys.sorted(),
            entryTypeCounts = entryTypeCounts,
            nodeCount = nodesById.size,
            edgeCount = edges.size,
            flowCount = flowIndexEntries.size,
            flows = flowIndexEntries
        )

        mapper.writerWithDefaultPrettyPrinter()
            .writeValue(outDir.resolve("index.json").toFile(), indexPayload)
    }
}

private fun normalizeNode(raw: RawNode): Node {
    val id = raw.id ?: ""
    val name = raw.name?.takeIf { it.isNotBlank() } ?: shortNameForId(id)
    val fullName = raw.fullName?.takeIf { it.isNotBlank() } ?: id
    val type = raw.type?.takeIf { it.isNotBlank() } ?: "unknown"
    return Node(id = id, name = name, fullName = fullName, type = type)
}

private fun shortNameForId(nodeId: String): String {
    val normalized = nodeId.replace('$', '.')
    return normalized.substringAfterLast('.')
}

private fun collectFlow(
    entryId: String,
    nodesById: Map<String, Node>,
    adjacency: Map<String, List<Edge>>
): Pair<List<Node>, List<Edge>> {
    val visitedNodes = linkedSetOf(entryId)
    val visitedEdges = linkedSetOf<EdgeKey>()
    val stack = ArrayDeque<String>()
    stack.add(entryId)

    while (stack.isNotEmpty()) {
        val current = stack.removeLast()
        adjacency[current].orEmpty().forEach { edge ->
            val edgeKey = EdgeKey(edge.fromId, edge.toId, edge.type, edge.label)
            if (!visitedEdges.add(edgeKey)) return@forEach
            if (!visitedNodes.contains(edge.toId)) {
                visitedNodes.add(edge.toId)
                stack.add(edge.toId)
            }
        }
    }

    val nodes = visitedNodes.map { nodeId ->
        nodesById[nodeId] ?: Node(
            id = nodeId,
            name = shortNameForId(nodeId),
            fullName = nodeId,
            type = "unknown"
        )
    }.sortedBy { it.id }

    val edges = visitedEdges.map { Edge(it.fromId, it.toId, it.type, it.label) }
        .sortedWith(compareBy<Edge> { it.fromId }.thenBy { it.toId }.thenBy { it.type })

    return nodes to edges
}

private fun renderMermaid(nodes: List<Node>, edges: List<Edge>): String {
    val idMap = linkedMapOf<String, String>()
    val lines = mutableListOf("flowchart TD")
    val usedClasses = mutableSetOf<String>()

    nodes.forEachIndexed { index, node ->
        val localId = "N${index + 1}"
        idMap[node.id] = localId
        val labelText = sanitizeLabel(formatNodeLabel(node))
        val nodeType = node.type.ifBlank { "unknown" }
        val shape = TYPE_SHAPES[nodeType] ?: "rectangle"
        lines.add("  $localId${wrapLabel(labelText, shape)}")
        lines.add("  class $localId $nodeType")
        usedClasses.add(nodeType)
    }

    edges.forEach { edge ->
        val fromId = idMap[edge.fromId]
        val toId = idMap[edge.toId]
        if (fromId != null && toId != null) {
            lines.add("  $fromId -->|${edge.type}| $toId")
        }
    }

    usedClasses.sorted().forEach { className ->
        val style = CLASS_DEFS[className]
        if (style != null) {
            lines.add("  classDef $className $style")
        }
    }

    return lines.joinToString("\n", postfix = "\n")
}

private fun formatNodeLabel(node: Node): String {
    var text = node.name.ifBlank { node.id }
    LABEL_PREFIXES.forEach { prefix ->
        if (text.startsWith(prefix)) {
            text = text.removePrefix(prefix)
            return@forEach
        }
    }
    return text
}

private fun sanitizeLabel(text: String): String {
    return text
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "'")
        .replace("\n", " ")
        .replace("\r", " ")
}

private fun wrapLabel(label: String, shape: String): String {
    return when (shape) {
        "round" -> "($label)"
        "stadium" -> "([$label])"
        "circle" -> "(($label))"
        "diamond" -> "{$label}"
        "hexagon" -> "{{$label}}"
        "subroutine" -> "[[$label]]"
        "cylinder" -> "[($label)]"
        "parallelogram" -> "[/$label/]"
        else -> "[$label]"
    }
}

private fun slugify(text: String, used: MutableSet<String>): String {
    var slug = text.replace(Regex("[^A-Za-z0-9]+"), "_").trim('_')
    if (slug.isEmpty()) slug = "entry"
    slug = slug.take(80)
    if (used.contains(slug)) {
        val digest = MessageDigest.getInstance("MD5")
            .digest(text.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
        slug = "${slug}_${digest.take(8)}"
    }
    used.add(slug)
    return slug
}

private data class EdgeKey(
    val fromId: String,
    val toId: String,
    val type: String,
    val label: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
private data class RawNode(
    val id: String? = null,
    val name: String? = null,
    val fullName: String? = null,
    val type: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
private data class RawEdge(
    val fromId: String? = null,
    val toId: String? = null,
    val type: String? = null,
    val label: String? = null
)

private data class Node(
    val id: String,
    val name: String,
    val fullName: String,
    val type: String
)

private data class Edge(
    val fromId: String,
    val toId: String,
    val type: String,
    val label: String?
)

private data class FlowPayload(
    val entryId: String,
    val entryType: String,
    val nodeCount: Int,
    val edgeCount: Int,
    val nodes: List<Node>,
    val edges: List<Edge>
)

private data class FlowIndexEntry(
    val entryId: String,
    val entryType: String,
    val nodeCount: Int,
    val edgeCount: Int,
    val json: String,
    val mermaid: String
)

private data class IndexPayload(
    val generatedAt: String,
    val inputDirs: List<String>,
    val entryTypes: List<String>,
    val entryTypeCounts: Map<String, Int>,
    val nodeCount: Int,
    val edgeCount: Int,
    val flowCount: Int,
    val flows: List<FlowIndexEntry>
)

private val ALLOWED_EDGE_TYPES = setOf(
    "ControllerMethodToCommand",
    "ControllerMethodToQuery",
    "ControllerMethodToCli",
    "CommandSenderMethodToCommand",
    "QuerySenderMethodToQuery",
    "CliSenderMethodToCli",
    "ValidatorToQuery",
    "CommandToCommandHandler",
    "QueryToQueryHandler",
    "CliToCliHandler",
    "CommandHandlerToEntityMethod",
    "EntityMethodToEntityMethod",
    "EntityMethodToDomainEvent",
    "DomainEventToHandler",
    "DomainEventHandlerToCommand",
    "DomainEventHandlerToQuery",
    "DomainEventHandlerToCli",
    "DomainEventToIntegrationEvent",
    "IntegrationEventToHandler",
    "IntegrationEventHandlerToCommand",
    "IntegrationEventHandlerToQuery",
    "IntegrationEventHandlerToCli"
)

private val ENTRY_NODE_TYPES = setOf(
    "controllermethod",
    "commandsendermethod",
    "querysendermethod",
    "clisendermethod",
    "validator",
    "integrationevent"
)

private val LABEL_PREFIXES = listOf(
    "edu.only4.danmuku.",
    "com.only4."
)

private val TYPE_SHAPES = mapOf(
    "controller" to "rectangle",
    "controllermethod" to "stadium",
    "commandsendermethod" to "parallelogram",
    "querysendermethod" to "parallelogram",
    "clisendermethod" to "parallelogram",
    "validator" to "parallelogram",
    "command" to "hexagon",
    "commandhandler" to "subroutine",
    "query" to "hexagon",
    "queryhandler" to "subroutine",
    "cli" to "hexagon",
    "clihandler" to "subroutine",
    "aggregate" to "cylinder",
    "entitymethod" to "round",
    "domainevent" to "circle",
    "domaineventhandler" to "subroutine",
    "integrationevent" to "circle",
    "integrationeventhandler" to "subroutine",
    "integrationeventconverter" to "subroutine",
    "unknown" to "rectangle"
)

private val CLASS_DEFS = mapOf(
    "controller" to "fill:#F9FAFB,stroke:#9CA3AF,color:#111827",
    "controllermethod" to "fill:#FDE68A,stroke:#B45309,color:#111827",
    "commandsendermethod" to "fill:#FED7AA,stroke:#C2410C,color:#111827",
    "querysendermethod" to "fill:#BFDBFE,stroke:#1D4ED8,color:#0F172A",
    "clisendermethod" to "fill:#BBF7D0,stroke:#15803D,color:#0F172A",
    "validator" to "fill:#FEF3C7,stroke:#B45309,color:#111827",
    "command" to "fill:#FDBA74,stroke:#C2410C,color:#111827",
    "commandhandler" to "fill:#F97316,stroke:#9A3412,color:#111827",
    "query" to "fill:#93C5FD,stroke:#1D4ED8,color:#0F172A",
    "queryhandler" to "fill:#3B82F6,stroke:#1E40AF,color:#F8FAFC",
    "cli" to "fill:#86EFAC,stroke:#15803D,color:#0F172A",
    "clihandler" to "fill:#22C55E,stroke:#166534,color:#F0FDF4",
    "aggregate" to "fill:#E5E7EB,stroke:#6B7280,color:#111827",
    "entitymethod" to "fill:#F3F4F6,stroke:#6B7280,color:#111827",
    "domainevent" to "fill:#FECACA,stroke:#B91C1C,color:#111827",
    "domaineventhandler" to "fill:#F87171,stroke:#991B1B,color:#111827",
    "integrationevent" to "fill:#C7D2FE,stroke:#4338CA,color:#111827",
    "integrationeventhandler" to "fill:#818CF8,stroke:#3730A3,color:#111827",
    "integrationeventconverter" to "fill:#A5B4FC,stroke:#3730A3,color:#111827",
    "unknown" to "fill:#F3F4F6,stroke:#9CA3AF,color:#111827"
)
