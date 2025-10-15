// The settings file is the entry point of every Gradle build.
// Its primary purpose is to define the subprojects.
// It is also used for some aspects of project-wide configuration, like managing plugins, dependencies, etc.
// https://docs.gradle.org/current/userguide/settings_file_basics.html

pluginManagement {
    repositories {
        mavenLocal()
        repositories {
            mavenLocal()
            maven {
                credentials {
                    username = providers.gradleProperty("aliyun.maven.username").get()
                    password = providers.gradleProperty("aliyun.maven.password").get()
                }
                url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/code-gen")
            }
            maven {
                url = uri("https://maven.aliyun.com/repository/public")
            }
            mavenCentral()
        }
        maven {
            credentials {
                username = providers.gradleProperty("aliyun.maven.username").get()
                password = providers.gradleProperty("aliyun.maven.password").get()
            }
            url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/cap4k")
        }
        maven {
            credentials {
                username = providers.gradleProperty("aliyun.maven.username").get()
                password = providers.gradleProperty("aliyun.maven.password").get()
            }
            url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/only-engine")
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // Use Maven Central as the default repository (where Gradle will download dependencies) in all subprojects.
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        repositories {
            mavenLocal()
            maven {
                credentials {
                    username = providers.gradleProperty("aliyun.maven.username").get()
                    password = providers.gradleProperty("aliyun.maven.password").get()
                }
                url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/code-gen")
            }
            maven {
                url = uri("https://maven.aliyun.com/repository/public")
            }
            mavenCentral()
        }
        maven {
            credentials {
                username = providers.gradleProperty("aliyun.maven.username").get()
                password = providers.gradleProperty("aliyun.maven.password").get()
            }
            url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/cap4k")
        }
        maven {
            credentials {
                username = providers.gradleProperty("aliyun.maven.username").get()
                password = providers.gradleProperty("aliyun.maven.password").get()
            }
            url = uri("https://packages.aliyun.com/67053c6149e9309ce56b9e9e/maven/only-engine")
        }

        mavenCentral()
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
    }
}

plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("only-danmuku-adapter")
include("only-danmuku-application")
include("only-danmuku-domain")

rootProject.name = "only-danmuku"

include("only-danmuku-start")
