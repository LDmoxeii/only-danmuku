// [cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]
plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("com.only4.codegen") version "0.2.0-SNAPSHOT"
    id("com.only4.cap4k.codeanalysis") version "0.4.2-SNAPSHOT"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

// Apply the code analysis compiler plugin to Kotlin JVM subprojects as well
subprojects {
    plugins.withId("org.jetbrains.kotlin.jvm") {
        pluginManager.apply("com.only4.cap4k.codeanalysis")
    }
}

// Merge all subprojects' cap4k-code-analysis outputs into a single view
tasks.register("mergeCodeAnalysis") {
    group = "code-analysis"
    description = "Merge nodes.json and rels.json from all subprojects into build/cap4k-code-analysis/merged-*.json"
    doLast {
        val mergedNodes = LinkedHashMap<String, Map<String, Any?>>()
        val mergedRels = ArrayList<Map<String, Any?>>()
        val relKeys = HashSet<String>()

        subprojects.forEach { p ->
            val dir = p.layout.buildDirectory.dir("cap4k-code-analysis").get().asFile
            val nodesFile = dir.resolve("nodes.json")
            val relsFile = dir.resolve("rels.json")
            if (nodesFile.exists()) {
                @Suppress("UNCHECKED_CAST")
                val arr = groovy.json.JsonSlurper().parse(nodesFile) as? List<Map<String, Any?>>
                arr?.forEach { n ->
                    val id = (n["id"] as? String) ?: return@forEach
                    mergedNodes.putIfAbsent(id, n)
                }
            }
            if (relsFile.exists()) {
                @Suppress("UNCHECKED_CAST")
                val arr = groovy.json.JsonSlurper().parse(relsFile) as? List<Map<String, Any?>>
                arr?.forEach { r ->
                    val fromId = r["fromId"] as? String ?: return@forEach
                    val toId = r["toId"] as? String ?: return@forEach
                    val type = r["type"] as? String ?: return@forEach
                    val key = "$fromId|$toId|$type"
                    if (relKeys.add(key)) mergedRels.add(r)
                }
            }
        }

        val outDir = layout.buildDirectory.dir("cap4k-code-analysis").get().asFile.apply { mkdirs() }
        val nodesOut = outDir.resolve("merged-nodes.json")
        val relsOut = outDir.resolve("merged-rels.json")
        nodesOut.writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(mergedNodes.values)))
        relsOut.writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(mergedRels)))
        println("Merged ${mergedNodes.size} nodes and ${mergedRels.size} relationships into ${outDir.relativeTo(projectDir)}")
    }
}

// Generate a single Mermaid overview diagram from merged JSON
tasks.register("generateMermaidOverview") {
    group = "code-analysis"
    description = "Render architecture-overview.mmd from merged JSON"
    dependsOn("mergeCodeAnalysis")
    doLast {
        val outBase = layout.buildDirectory.dir("cap4k-code-analysis").get().asFile
        val nodesFile = outBase.resolve("merged-nodes.json")
        val relsFile = outBase.resolve("merged-rels.json")
        require(nodesFile.exists()) { "Missing merged-nodes.json. Run mergeCodeAnalysis first." }
        require(relsFile.exists()) { "Missing merged-rels.json. Run mergeCodeAnalysis first." }

        @Suppress("UNCHECKED_CAST")
        val nodes = groovy.json.JsonSlurper().parse(nodesFile) as List<Map<String, Any?>>
        @Suppress("UNCHECKED_CAST")
        val rels = groovy.json.JsonSlurper().parse(relsFile) as List<Map<String, Any?>>

        fun labelOfType(type: String): String = when (type) {
            "ControllerToCommand", "ControllerMethodToCommand", "EndpointToCommand", "CommandSenderToCommand", "CommandSenderMethodToCommand" -> "Send"
            "CommandToAggregate", "CommandToEntityMethod", "EntityMethodToEntityMethod" -> "Invoke"
            "AggregateToDomainEvent", "EntityMethodToDomainEvent" -> "Publish"
            "DomainEventToHandler", "IntegrationEventToHandler" -> "Handle"
            "DomainEventHandlerToCommand", "IntegrationEventHandlerToCommand" -> "Send"
            "DomainEventToIntegrationEvent" -> "Convert"
            "AggregateToRepo" -> "Query/Load"
            "HandlerToUow" -> "Persist & Save"
            else -> type
        }

        val idMap = LinkedHashMap<String, String>()
        var counter = 1
        nodes.forEach { n ->
            val full = (n["fullName"] as? String) ?: return@forEach
            idMap.putIfAbsent(full, "N" + counter++)
        }

        val sb = StringBuilder()
        sb.appendLine("flowchart TD")
        sb.appendLine()
        nodes.forEach { n ->
            val full = (n["fullName"] as? String) ?: return@forEach
            val name = (n["name"] as? String) ?: full.substringAfterLast('.')
            val type = (n["type"] as? String) ?: ""
            val nodeId = idMap[full] ?: return@forEach
            val text = name.replace("\"", "&quot;")
            sb.appendLine("    " + nodeId + "[\"" + text + "\"]")
            sb.appendLine("    class " + nodeId + " " + type + ";")
        }
        sb.appendLine()
        sb.appendLine("    %% Relationships")
        rels.forEach { r ->
            val from = (r["fromId"] as? String) ?: return@forEach
            val to = (r["toId"] as? String) ?: return@forEach
            val type = (r["type"] as? String) ?: return@forEach
            val lbl = (r["label"] as? String) ?: labelOfType(type)
            val fromId = idMap[from] ?: return@forEach
            val toId = idMap[to] ?: return@forEach
            val safeLbl = lbl.replace("|", "/").replace("\"", "'")
            sb.appendLine("    " + fromId + " -->|" + safeLbl + "| " + toId)
        }
        sb.appendLine()
        sb.appendLine("    %% Styles")
        sb.appendLine("    classDef controller fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef controllermethod fill:#e3f2fd,stroke:#1976d2,stroke-width:2px;")
        sb.appendLine("    classDef endpoint fill:#e3f2fd,stroke:#42a5f5,stroke-width:2px;")
        sb.appendLine("    classDef commandsender fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef commandsendermethod fill:#e3f2fd,stroke:#1976d2,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef command fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef commandhandler fill:#e8f5e8,stroke:#388e3c,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef aggregate fill:#fff8e1,stroke:#ff8f00,stroke-width:3px,font-weight:bold;")
        sb.appendLine("    classDef entitymethod fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px;")
        sb.appendLine("    classDef domainevent fill:#fff3e0,stroke:#e65100,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef domaineventhandler fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef integrationevent fill:#fce4ec,stroke:#880e4f,stroke-width:2px;")
        sb.appendLine("    classDef integrationeventhandler fill:#fce4ec,stroke:#ad1457,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef integrationeventconverter fill:#e3f2fd,stroke:#0277bd,stroke-width:2px;")
        sb.appendLine("    classDef repo fill:#d6dbdf,stroke:#85929e,stroke-width:2px;")
        sb.appendLine("    classDef uow fill:#f2f4f4,stroke:#7f8c8d,stroke-width:2px;")

        val outFile = outBase.resolve("architecture-overview.mmd")
        outFile.writeText(sb.toString())
        println("Wrote Mermaid diagram: " + outFile.relativeTo(projectDir))
    }
}

// Focused diagram for postVideo flow
tasks.register("generateMermaidPostVideoFlow") {
    group = "code-analysis"
    description = "Render a focused Mermaid diagram for CompatibleUCenterVideoPostController::postVideo"
    dependsOn("mergeCodeAnalysis")
    doLast {
        val outBase = layout.buildDirectory.dir("cap4k-code-analysis").get().asFile
        val nodesFile = outBase.resolve("merged-nodes.json")
        val relsFile = outBase.resolve("merged-rels.json")
        require(nodesFile.exists()) { "Missing merged-nodes.json. Run mergeCodeAnalysis first." }
        require(relsFile.exists()) { "Missing merged-rels.json. Run mergeCodeAnalysis first." }

        @Suppress("UNCHECKED_CAST")
        val nodes = groovy.json.JsonSlurper().parse(nodesFile) as List<Map<String, Any?>>
        @Suppress("UNCHECKED_CAST")
        val rels = groovy.json.JsonSlurper().parse(relsFile) as List<Map<String, Any?>>

        val nodesById = nodes.associateBy { (it["id"] as String) }

        val anchorId = "edu.only4.danmuku.adapter.portal.api.compatible.CompatibleUCenterVideoPostController::postVideo"
        require(nodesById.containsKey(anchorId)) { "Anchor node not found: $anchorId" }

        // Collect base subgraph: controller class, postVideo method, and controller -> command edges
        val includeIds = LinkedHashSet<String>()
        data class Edge(val from: String, val to: String, val label: String)
        val syntheticEdges = ArrayList<Edge>()

        includeIds.add(anchorId)
        val controllerClassId = anchorId.substringBefore("::")
        if (nodesById.containsKey(controllerClassId)) includeIds.add(controllerClassId)

        val controllerToCmd = rels.filter { (it["fromId"] as? String) == anchorId }
        val commandIds = controllerToCmd.mapNotNull { it["toId"] as? String }
        includeIds.addAll(commandIds)
        controllerToCmd.forEach { syntheticEdges.add(Edge(anchorId, it["toId"] as String, "sends")) }

        // Heuristics: infer command -> entitymethod for VideoPost based on command sources
        fun fqnRequestToKtFile(fqnReq: String): java.io.File? {
            // fqn like edu.only4.danmuku.application.commands.video_post.CreateVideoPostCmd.Request
            val base = fqnReq.removeSuffix(".Request")
            val objName = base.substringAfterLast('.')
            val pkg = base.removeSuffix(".$objName")
            val pkgPath = pkg.replace('.', '/')
            val appSrc = project.projectDir.resolve("only-danmuku-application/src/main/kotlin")
            val f = appSrc.resolve(pkgPath + "/" + objName + ".kt")
            return if (f.exists()) f else null
        }

        val entityMethodToEvents = rels.filter { it["type"] == "EntityMethodToDomainEvent" }
            .groupBy(
                keySelector = { it["fromId"] as String },
                valueTransform = { it["toId"] as String }
            )

        fun synthesizeEntityMethodNode(id: String, display: String) {
            if (!nodesById.containsKey(id)) {
                // Put a pseudo node descriptor into nodesById-like map via includeIds only;
                includeIds.add(id)
            }
        }

        // For each command, analyze source to infer called entity methods and link to domain events
        commandIds.forEach { cmdId ->
            val src = fqnRequestToKtFile(cmdId)?.takeIf { it.isFile }?.readText() ?: return@forEach
            // Heuristics
            val callsCreate = src.contains("Mediator.factories.create(") || src.contains("VideoPostFactory.Payload(")
            if (callsCreate) {
                val mId = "edu.only4.danmuku.domain.aggregates.video_post.VideoPost::onCreate"
                includeIds.add(cmdId); includeIds.add(mId)
                syntheticEdges.add(Edge(cmdId, mId, "execute"))
                // Attach events published by onCreate
                entityMethodToEvents[mId]?.forEach { ev ->
                    includeIds.add(ev)
                    syntheticEdges.add(Edge(mId, ev, "publishes"))
                }
            }
            if (src.contains("adjustStatusAfterEdit(")) {
                val mId = "edu.only4.danmuku.domain.aggregates.video_post.VideoPost::adjustStatusAfterEdit"
                includeIds.add(cmdId); includeIds.add(mId)
                syntheticEdges.add(Edge(cmdId, mId, "execute"))
                entityMethodToEvents[mId]?.forEach { ev ->
                    includeIds.add(ev)
                    syntheticEdges.add(Edge(mId, ev, "publishes"))
                }
            }
        }

        // Add domain event -> handler -> command edges by scanning subscribers
        val appSrcDir = project.projectDir.resolve("only-danmuku-application/src/main/kotlin")
        fun findHandlersFor(eventFqn: String): List<Triple<String, String, List<String>>> {
            val simple = eventFqn.substringAfterLast('.')
            val result = ArrayList<Triple<String, String, List<String>>>()
            appSrcDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.forEach { f ->
                val text = f.readText()
                if (text.contains("@EventListener(${simple}::class)")) {
                    val pkg = Regex("^package\\s+([a-zA-Z0-9_.]+)", RegexOption.MULTILINE).find(text)?.groupValues?.get(1) ?: return@forEach
                    val cls = Regex("class\\s+([A-Za-z0-9_]+)").find(text)?.groupValues?.get(1) ?: return@forEach
                    val handlerId = "$pkg.$cls::on"
                    val cmdNames = Regex("([A-Za-z0-9_]+)Cmd\\.Request").findAll(text).map { it.groupValues[1] }.toSet().toList()
                    // Resolve command FQNs from imports when possible
                    val imports = Regex("^import\\s+([a-zA-Z0-9_.]+)", RegexOption.MULTILINE).findAll(text).map { it.groupValues[1] }.toList()
                    val cmdFqns = cmdNames.map { name ->
                        imports.firstOrNull { it.endsWith(".$name") }?.let { it + ".Request" } ?: name + "Cmd.Request"
                    }
                    result.add(Triple(handlerId, eventFqn, cmdFqns))
                }
            }
            return result
        }

        // For each included event, wire handlers and the commands they send
        val currentEvents = syntheticEdges.filter { it.label == "publishes" }.map { it.to }.toSet()
        currentEvents.forEach { evFqn ->
            findHandlersFor(evFqn).forEach { (handlerId, eventId, cmdFqns) ->
                includeIds.add(handlerId)
                syntheticEdges.add(Edge(eventId, handlerId, "handles"))
                cmdFqns.forEach { cmd ->
                    includeIds.add(cmd)
                    syntheticEdges.add(Edge(handlerId, cmd, "sends"))
                }
            }
        }

        // Build an id map covering JSON nodes and synthetic nodes
        val idMap = LinkedHashMap<String, String>()
        var counter = 1
        fun ensureNode(id: String) {
            if (!idMap.containsKey(id)) idMap[id] = "N" + counter++
        }
        includeIds.forEach { ensureNode(it) }

        val sb = StringBuilder()
        sb.appendLine("flowchart TB")
        sb.appendLine()

        // Render nodes: merge JSON metadata when available, otherwise infer types from id
        includeIds.forEach { id ->
            val n = nodesById[id]
            val type = when {
                n != null -> (n["type"] as? String) ?: ""
                id == anchorId -> "controllermethod"
                id == controllerClassId -> "controller"
                id.endsWith(".Request") -> "command"
                id.contains("::") && id.contains("domain.aggregates") -> "entitymethod"
                id.contains("DomainEventSubscriber::on") -> "domaineventhandler"
                id.contains("DomainEvent") -> "domainevent"
                else -> ""
            }
            val full = (n?.get("fullName") as? String) ?: id
            var label = (n?.get("name") as? String) ?: run {
                if (id.contains("::")) {
                    val cls = id.substringBefore("::").substringAfterLast('.')
                    val m = id.substringAfter("::")
                    "$cls.$m"
                } else id.substringAfterLast('.')
            }
            if (type == "command") {
                val last = full.substringAfterLast('.')
                label = if (last == "Request") full.substringBeforeLast('.').substringAfterLast('.') else last
            }
            val nodeId = idMap[id] ?: return@forEach
            val text = label.replace("\"", "&quot;")
            sb.appendLine("    " + nodeId + "[\"" + text + "\"]")
            if (type.isNotEmpty()) sb.appendLine("    class " + nodeId + " " + type + ";")
        }

        sb.appendLine()
        sb.appendLine("    %% Relationships")
        syntheticEdges.forEach { e ->
            val fromId = idMap[e.from] ?: return@forEach
            val toId = idMap[e.to] ?: return@forEach
            val lbl = e.label
            val safeLbl = lbl.replace("|", "/").replace("\"", "'")
            sb.appendLine("    " + fromId + " -->|" + safeLbl + "| " + toId)
        }

        sb.appendLine()
        sb.appendLine("    %% Styles")
        sb.appendLine("    classDef controller fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef controllermethod fill:#e3f2fd,stroke:#1976d2,stroke-width:2px;")
        sb.appendLine("    classDef endpoint fill:#e3f2fd,stroke:#42a5f5,stroke-width:2px;")
        sb.appendLine("    classDef commandsender fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef commandsendermethod fill:#e3f2fd,stroke:#1976d2,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef command fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef commandhandler fill:#e8f5e8,stroke:#388e3c,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef aggregate fill:#fff8e1,stroke:#ff8f00,stroke-width:3px,font-weight:bold;")
        sb.appendLine("    classDef entitymethod fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px;")
        sb.appendLine("    classDef domainevent fill:#fff3e0,stroke:#e65100,stroke-width:2px,font-style:italic;")
        sb.appendLine("    classDef domaineventhandler fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef integrationevent fill:#fce4ec,stroke:#880e4f,stroke-width:2px;")
        sb.appendLine("    classDef integrationeventhandler fill:#fce4ec,stroke:#ad1457,stroke-width:2px,font-weight:bold;")
        sb.appendLine("    classDef integrationeventconverter fill:#e3f2fd,stroke:#0277bd,stroke-width:2px;")
        sb.appendLine("    classDef repo fill:#d6dbdf,stroke:#85929e,stroke-width:2px;")
        sb.appendLine("    classDef uow fill:#f2f4f4,stroke:#7f8c8d,stroke-width:2px;")

        // Write to project build output and workspace-level design folder
        val outFile = outBase.resolve("ucenter_post_video_flow.mmd")
        outFile.writeText(sb.toString())

        val workspaceDesignDir = project.rootDir.parentFile?.resolve("design") ?: project.rootDir.resolve("..\\design")
        workspaceDesignDir.mkdirs()
        val designOut = workspaceDesignDir.resolve("ucenter_post_video_flow.mmd")
        designOut.writeText(sb.toString())

        // Also copy under only-danmuku/design for convenience
        val moduleDesignDir = project.projectDir.resolve("design")
        moduleDesignDir.mkdirs()
        val moduleDesignOut = moduleDesignDir.resolve("ucenter_post_video_flow.mmd")
        moduleDesignOut.writeText(sb.toString())

        println("Wrote focused Mermaid: " + outFile.relativeTo(projectDir))
        println("Also copied to: " + designOut)
        println("Also copied to: " + moduleDesignOut.relativeTo(project.projectDir))
    }
}

codegen {
    basePackage.set("edu.only4.danmuku")
    archTemplate.set("cap4k-ddd-codegen-template-multi-nested.json")
    designFiles.from(fileTree("design") {
            include("**/*_gen.json")
        })

    database {
        url.set("jdbc:mysql://localhost:3306/only_danmuku?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
        username.set("root")
        password.set("123456")
        schema.set("only_danmuku")
        tables.set("")
        ignoreTables.set("")
    }

    generation {
        versionField.set("version")
        deletedField.set("deleted")
        readonlyFields.set("id")
        generateAggregate.set(false)
        repositorySupportQuerydsl.set(false)
        ignoreFields.set("create_user_id,create_by,create_time,update_user_id,update_by,update_time,deleted")
        rootEntityBaseClass.set("AuditedFieldsEntity")
        entityBaseClass.set("AuditedFieldsEntity")
        typeMapping.set(mapOf(
            "UserMessageExtend" to "edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend",
            "AuditedFieldsEntity" to "edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity"
        ))
    }
}
