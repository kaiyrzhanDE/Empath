import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.jetbrains
import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.kotlin
import gradle.kotlin.dsl.plugins._3fd00c8e1a12094f0cc7c063225041f7.multiplatform
import kaiyrzhan.de.empath.gradle.DESKTOP
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.ProjectTargets
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.plugins

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
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
