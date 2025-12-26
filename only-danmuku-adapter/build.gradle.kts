plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("org.jetbrains.kotlin.kapt")
}

dependencies {
    api(libs.spring.data)
    api(libs.spring.web)
    api(libs.spring.messaging)
    api(libs.spring.elasticsearch)
    api(libs.druid)
    api(libs.mysql)
    api(libs.opentelemetry.api)

    api(libs.jimmer.starter)

    api(libs.engine.captcha)
    api(libs.engine.security)
    api(libs.engine.satoken)
    api(libs.engine.web)
    api(libs.engine.doc)
    api(libs.engine.translation)
    api(libs.engine.oss)

    api(project(":only-danmuku-application"))

    // MapStruct for DTO <-> Command/Query mapping
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.unmappedTargetPolicy", "IGNORE")
    }
}
