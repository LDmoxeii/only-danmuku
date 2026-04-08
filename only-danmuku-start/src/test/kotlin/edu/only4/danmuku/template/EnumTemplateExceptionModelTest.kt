package edu.only4.danmuku.template

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class EnumTemplateExceptionModelTest {

    @Test
    fun `enum template should use the new exception model`() {
        val templatePath = resolveTemplatePath()
        val content = Files.readString(templatePath)

        assertFalse(content.contains("KnownException"))
        assertTrue(content.contains("RequestException"))
        assertTrue(content.contains("SystemException"))
        assertTrue(content.contains("fun fromDbValue(value: Int?):"))
        assertTrue(content.contains("return fromDbValue(dbData)"))
    }

    private fun resolveTemplatePath(): Path {
        val current = Path.of(System.getProperty("user.dir")).toAbsolutePath()
        val candidates = listOf(
            current.resolve("template").resolve("_tpl").resolve("enum.kt.peb"),
            current.parent?.resolve("template")?.resolve("_tpl")?.resolve("enum.kt.peb")
        )
        return candidates.filterNotNull().firstOrNull { Files.exists(it) }
            ?: error("enum template file not found from $current")
    }
}
