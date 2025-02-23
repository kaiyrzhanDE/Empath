plugins {
    alias(libs.plugins.empath.kmp.library.ios)
    alias(libs.plugins.empath.compose.ios)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.composeApp)
            // Необходимо явно добавить все зависимости для работы
            // приложения на iOS, чтобы они попали в XCFramework
            implementation(compose.runtime)
            implementation(compose.ui)
        }
    }
}
