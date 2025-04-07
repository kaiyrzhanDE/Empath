import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.enableExplicitApi
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)
plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.serialization.get().pluginId)

enableExplicitApi()

kmpConfig {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.annotation)
            implementation(libs.kotlinx.datetime)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            implementation(libs.paging.common)
        }
    }
}
