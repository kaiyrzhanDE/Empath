plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.ktor)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.utils)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.network"
}
