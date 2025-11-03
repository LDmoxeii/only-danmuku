plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.ksp)
}

dependencies {
    api(platform(libs.spring.boot.dependencies))
    compileOnly(libs.spring.data)

    api(libs.annotation)

    implementation(libs.hutool.crypto)

    api(libs.ddd.starter)

    // JSON utils for JPA converters
    implementation(libs.engine.json)

    ksp("com.only4:ksp-processor:0.2.0-SNAPSHOT")
}
