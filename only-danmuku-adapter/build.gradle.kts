plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    api(libs.spring.data)
    api(libs.spring.web)
    api(libs.spring.messaging)
    api(libs.druid)
    api(libs.mysql)

    api(libs.jimmer.starter)
    api(libs.blazebit.persistence.hibernate)

    api(libs.engine.captcha)
    api(libs.engine.security)
    api(libs.engine.satoken)
    api(libs.engine.web)
    api(libs.engine.doc)
    api(libs.engine.translation)

    implementation(libs.blazebit.persistence.querydsl)

    api(project(":only-danmuku-application"))
}
