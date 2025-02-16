plugins {
    id(libs.plugins.convention.kmp.library.ios.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.ios.get().pluginId)
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
