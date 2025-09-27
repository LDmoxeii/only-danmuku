
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
}
