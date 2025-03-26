@file:Suppress("UnstableApiUsage")

rootProject.name = "Empath"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":EmpathApp")
project(":EmpathApp").name = "EmpathApp"

include(":features:auth:ui")
include(":features:auth:domain")
include(":features:auth:data")

include(":features:profile:ui")
include(":features:profile:domain")
include(":features:profile:data")

include(":core:uikit")
include(":core:utils")
include(":core:network")
