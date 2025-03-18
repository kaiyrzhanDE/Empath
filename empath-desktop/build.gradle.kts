import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.empath.kmp.library.desktop)
    alias(libs.plugins.empath.compose.desktop)
}

kotlin {
    sourceSets {
        desktopMain.dependencies {
            implementation(projects.composeApp)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "kaiyrzhan.de.empath.Empath"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Empath"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/ic_app_dmg.icns"))
            }
            windows {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/ic_app_msi.ico"))
            }
            linux {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/ic_app_deb.png"))
            }
        }
    }
}
