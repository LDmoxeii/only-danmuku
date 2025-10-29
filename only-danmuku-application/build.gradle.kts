import jdk.tools.jlink.resources.plugins

plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.ksp)
}

dependencies {
    api(libs.spring.data)
    api(libs.spring.validation)
    api(libs.spring.web)
    api(project(":only-danmuku-domain"))

    api(libs.ddd.integration.event.http)
    api(libs.ddd.integration.event.http.jpa)

    api(libs.hutool.core)

    api(libs.engine.common)
    api(libs.engine.json)
    api(libs.engine.redis)

    implementation(libs.jimmer.core)
    compileOnly(libs.jimmer.sql)
    ksp(libs.jimmer.ksp)
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
