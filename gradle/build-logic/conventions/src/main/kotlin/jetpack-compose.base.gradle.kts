import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.compose.compiler.get().pluginId)

androidConfig {
    buildFeatures {
        compose = true
    }
}
