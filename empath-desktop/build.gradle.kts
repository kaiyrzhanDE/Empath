import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id(libs.plugins.convention.kmp.library.desktop.get().pluginId)
    id(libs.plugins.convention.jetbrains.compose.desktop.get().pluginId)
}

kotlin {
    sourceSets {
        desktopMain.dependencies {
            implementation(projects.composeApp)
        }
    }
}

compose.desktop {
    application {
        mainClass = "kaiyrzhan.de.empath.Empath"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kaiyrzhan.de.empath"
            packageVersion = "1.0.0"
        }
    }
}
