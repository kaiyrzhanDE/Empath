plugins {
    id(libs.plugins.convention.kmp.library.all.get().pluginId)
    id(libs.plugins.convention.koin.get().pluginId)
}

android {
    namespace = "kaiyrzhan.de.empath.features.auth.domain"
}
