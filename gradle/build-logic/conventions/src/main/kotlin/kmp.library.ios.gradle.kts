import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.config.IOSPlatform
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.config.kmpIosPlatforms
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
plugins.applyIfNeeded("kmp.library.base")

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
            }
        }
}
