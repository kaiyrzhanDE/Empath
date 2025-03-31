import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.config.IOSPlatform
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.config.kmpIosPlatforms
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
plugins.applyIfNeeded(libs.plugins.empath.kmp.library.base.get().pluginId)

kmpConfig {
    kmpIosPlatforms
        .asSequence()
        .map {
            when (it) {
                IOSPlatform.ARM_64 -> iosArm64()
                IOSPlatform.SIMULATOR_ARM64 -> iosSimulatorArm64()
                IOSPlatform.SIMULATOR_X64 -> iosX64()
            }
        }.forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = project.name
                isStatic = true
                export(libs.arkivanov.decompose)
                export(libs.arkivanov.decompose.compose)
                export(libs.arkivanov.essenty.lifecycle)
                export(libs.arkivanov.essenty.stateKeeper)
                export(libs.arkivanov.essenty.backHandler)

                export(project(":core:uikit"))
                export(project(":core:utils"))
                export(project(":core:network"))

                export(project(":features:auth:ui"))
                export(project(":features:auth:domain"))
                export(project(":features:auth:data"))

                export(project(":features:profile:ui"))
                export(project(":features:profile:domain"))
                export(project(":features:profile:data"))

                export(project(":features:articles:domain"))
                export(project(":features:articles:data"))

                export(project(":features:file-storage:domain"))
                export(project(":features:file-storage:data"))

            }
        }
}
