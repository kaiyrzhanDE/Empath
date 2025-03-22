plugins {
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.compose.all)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.network)
            implementation(projects.core.utils)

            implementation(projects.features.auth.data)
            implementation(projects.features.auth.domain)
            implementation(projects.features.auth.ui)

            implementation(projects.features.profile.data)
            implementation(projects.features.profile.domain)
            implementation(projects.features.profile.ui)
        }
    }
}

compose.resources {
    publicResClass = false
    generateResClass = always
}

android {
    namespace = "kaiyrzhan.de.empath.composeapp"
}
