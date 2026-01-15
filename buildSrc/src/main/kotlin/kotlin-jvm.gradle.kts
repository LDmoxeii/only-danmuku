// The code in this file is a convention plugin - a Gradle mechanism for sharing reusable build logic.
// `buildSrc` is a Gradle-recognized directory and every plugin there will be easily available in the rest of the build.
package buildsrc.convention

import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.time.Duration

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin in JVM projects.
    kotlin("jvm")
    // Apply the Kotlin Spring plugin for better Spring framework integration.
    kotlin("plugin.spring")
    // Apply the Kotlin JPA plugin for JPA entity class generation.
    kotlin("plugin.jpa")
}

group = ""
version = "unspecified"

kotlin {
    // Use a specific Java version to make it easier to work in different environments.
    jvmToolchain(17)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    timeout.set(Duration.ofMinutes(10))
    jvmArgs(
        "-Xmx2g",
        "-Xms512m",
        "-XX:MaxMetaspaceSize=512m",
    )
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}

// Cap4k code analysis compiler plugin (IR/K2)
val cap4kPluginDir = file("D:/ideaProjects/code-analysis/cap4k/cap4k-code-analysis-compiler-plugin/build/libs")
val cap4kCoreDir = file("D:/ideaProjects/code-analysis/cap4k/cap4k-extensions-code-analysis-core/build/libs")
val cap4kPluginJar = cap4kPluginDir.listFiles()
    ?.firstOrNull { it.name.startsWith("cap4k-code-analysis-compiler-plugin") && it.name.endsWith(".jar") && !it.name.endsWith("-sources.jar") }
val cap4kCoreJar = cap4kCoreDir.listFiles()
    ?.firstOrNull { it.name.startsWith("cap4k-extensions-code-analysis-core") && it.name.endsWith(".jar") && !it.name.endsWith("-sources.jar") }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    if (cap4kPluginJar != null && cap4kCoreJar != null) {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xplugin=${cap4kPluginJar.absolutePath}",
                "-Xplugin=${cap4kCoreJar.absolutePath}"
            )
        }
    }
}
