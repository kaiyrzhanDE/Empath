plugins {
    alias(libs.plugins.android.library)
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.ktor.get().pluginId)
    id(libs.plugins.convention.koin.get().pluginId)
}

android {
    namespace = "kaiyrzhan.de.empath.core.network"
}
