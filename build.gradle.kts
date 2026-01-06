// [cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]
plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("com.only4.codegen") version "0.2.0-SNAPSHOT"
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

codegen {
    basePackage.set("edu.only4.danmuku")
    archTemplate.set("cap4k-ddd-codegen-template-multi-nested.json")
    designFiles.from(
        fileTree("iterate") {
            include("**/*_gen.json")
            exclude("achrived/**", "deprecated/**")
        }
    )

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
            "MultipartFile" to "org.springframework.web.multipart.MultipartFile",
            "UserMessageExtend" to "edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend",
            "AuditedFieldsEntity" to "edu.only4.danmuku.domain._share.audit.AuditedFieldsEntity",
            "UserType" to "edu.only4.danmuku.domain.aggregates.user.enums.UserType",
            "UserTypeTranslation" to "edu.only4.danmuku.adapter.domain.translation.user.UserTypeTranslation",
            "PostType" to "edu.only4.danmuku.domain.aggregates.video_post.enums.PostType",
            "PostTypeTranslation" to "edu.only4.danmuku.adapter.domain.translation.video_post.PostTypeTranslation",
            "EncryptMethod" to "edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod",
            "EncryptMethodTranslation" to "edu.only4.danmuku.adapter.domain.translation.video_post.EncryptMethodTranslation"
        ))
    }
}
