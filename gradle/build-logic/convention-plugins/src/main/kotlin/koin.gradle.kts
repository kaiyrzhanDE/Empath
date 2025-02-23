import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs
import org.gradle.kotlin.dsl.invoke

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.serialization.get().pluginId)
plugins.applyIfNeeded(libs.plugins.google.ksp.get().pluginId)

kmpConfig {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
