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
    designFiles.from(fileTree("design") {
            include("**/*_gen.json")
        })

    database {
        url.set("jdbc:mysql://localhost:3306/only_danmuku?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
        username.set("root")
        password.set("123456")
        schema.set("only_danmuku")
        tables.set("customer_message")
        ignoreTables.set("")
    }

    generation {
        versionField.set("version")
        deletedField.set("deleted")
        readonlyFields.set("id")
        generateAggregate.set(false)
        repositorySupportQuerydsl.set(false)
        typeMapping.set(mapOf(
            "UserMessageExtend" to "edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend"
        ))
    }
}
