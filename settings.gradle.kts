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

private fun isDirectoryGradleModule(file: File): Boolean {
    val buildGradleFile = File(file, "build.gradle.kts")
    return file.isDirectory && buildGradleFile.isFile && buildGradleFile.exists()
}

private fun includeAllModules(directory: String) {
    file(directory)
        .listFiles { directoryFile -> isDirectoryGradleModule(directoryFile) }
        ?.forEach { directoryFile -> include(":${directory.replace('/', ':')}:${directoryFile.name}") }
}

includeAllModules(directory = "core")
includeAllModules(directory = "features/auth")
includeAllModules(directory = "features/profile")
includeAllModules(directory = "features/posts")
includeAllModules(directory = "features/vacancies")
includeAllModules(directory = "features/file-storage")

include(":empath-app")
project(":empath-app").name = "EmpathApp"
