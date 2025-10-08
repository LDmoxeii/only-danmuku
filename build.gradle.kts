// [cap4k-ddd-codegen-gradle-plugin:do-not-overwrite]
plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("com.only4.cap4k.ddd.codegen") version "0.3.10-SNAPSHOT"
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

cap4kCodegen {
    basePackage.set("com.edu.only4.danmuku")
    archTemplate.set("cap4k-ddd-codegen-template-multi-nested.json")
    designFiles.from(fileTree("design") {
        include("**/*_gen.txt")
    })

    database {
        url.set("jdbc:mysql://localhost:3306/only_danmuku?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
        username.set("root")
        password.set("123456")
        schema.set("only_danmuku")
        tables.set("user,customer_action,customer_video_series,customer_video_series_video")
        ignoreTables.set("")
    }

    generation {
        versionField.set("version")
        deletedField.set("deleted")
        readonlyFields.set("id")
        ignoreFields.set("create_user_id,create_by,create_time,update_user_id,update_by,update_time")
        generateParent.set(true)
    }
}
