import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.serialization.get().pluginId)

kmpConfig{
    sourceSets{
        commonMain.dependencies {
            api(libs.arkivanov.decompose)
            api(libs.arkivanov.decompose.compose)
            api(libs.arkivanov.essenty.lifecycle)
            api(libs.arkivanov.essenty.lifecycle.coroutines)
            api(libs.arkivanov.essenty.stateKeeper)
        }
    }
}
