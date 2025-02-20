plugins {
    alias(libs.plugins.android.library)
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.koin.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kermit)
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath.core.utils"
}
