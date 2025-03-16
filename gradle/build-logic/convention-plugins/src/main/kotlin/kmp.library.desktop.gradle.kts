import kaiyrzhan.de.empath.gradle.DESKTOP
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.ProjectTargets
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.empath.kmp.library.base.get().pluginId)

kmpConfig {
    jvm(DESKTOP) {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions.jvmTarget.set(libs.jvmTarget(ProjectTargets.Desktop))
            }
        }
    }

    sourceSets {
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
