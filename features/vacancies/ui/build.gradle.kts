plugins {
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.compose.all)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.features.vacancies.domain)
                implementation(projects.features.fileStorage.domain)
                implementation(libs.paging.compose)
            }
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.features.vacancies.ui"
}
