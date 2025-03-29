plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.ktor)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.network)
                implementation(projects.core.utils)
                implementation(projects.features.profile.domain)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.profile.data"
}
