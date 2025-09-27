plugins {
    kotlin("jvm") version "2.1.20"
    id("com.only4.cap4k.ddd.codegen") version "0.3.6-SNAPSHOT"
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

group = "edu.only4"
version = "0.0.1-SNAPSHOT"

cap4kCodegen {
    basePackage.set("com.edu.only4.danmuku")
    archTemplate.set("cap4k-ddd-codegen-template-multi-nested.json")

    database {
        url.set("jdbc:mysql://localhost:3306/only_danmuku?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
        username.set("root")
        password.set("123456")
        schema.set("only_danmuku")
    }

    generation {
        versionField.set("version")
        deletedField.set("deleted")
        readonlyFields.set("id,created_at,updated_at")
        ignoreFields.set("")
    }
}
