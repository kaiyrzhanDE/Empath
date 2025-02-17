import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.kmpConfig
import kaiyrzhan.de.empath.gradle.kotlinJvmCompilerOptions
import kaiyrzhan.de.empath.gradle.libs

plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.serialization.get().pluginId)
plugins.applyIfNeeded(libs.plugins.google.ksp.get().pluginId)
plugins.apply(libs.plugins.convention.kmp.library.all.get().pluginId)

kmpConfig {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktorfit)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.engine.okhttp)
        }

        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.ktor.client.engine.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.engine.darwin)
        }
    }
}
