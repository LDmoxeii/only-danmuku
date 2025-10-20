plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.spring.boot)
}

dependencies {
    api(libs.spring.boot.starter)
    api(libs.spring.actuator)
    api(libs.spring.configuration.processor)
    api(libs.cap4k.console)
    api(project(":only-danmuku-adapter"))
    api(project(":only-danmuku-application"))
    api(project(":only-danmuku-domain"))

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit.core)
    testImplementation(libs.mockk) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    testRuntimeOnly(libs.bundles.junit.runtime)

    testImplementation(libs.spring.boot.starter.test) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(libs.spring.boot.starter)

    testImplementation(libs.engine.redis)
}
