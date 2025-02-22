import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.all.get().pluginId)
    id(libs.plugins.convention.koin.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.uikit)
                implementation(projects.core.utils)
                implementation(projects.features.auth.domain)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.ui"
}
