plugins {
    alias(libs.plugins.empath.kmp.library.ios)
    alias(libs.plugins.empath.compose.ios)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.composeApp)
            api(projects.core.uikit)
            api(projects.core.network)
            api(projects.core.utils)

            api(projects.features.auth.ui)
            api(projects.features.auth.domain)
            api(projects.features.auth.data)

            api(projects.features.profile.ui)
            api(projects.features.profile.domain)
            api(projects.features.profile.data)

            // Необходимо явно добавить все зависимости для работы
            // приложения на iOS, чтобы они попали в XCFramework
            api(compose.runtime)
            api(compose.ui)
        }
    }
}
