import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.empath.kmp.library.all)
    alias(libs.plugins.empath.compose.all)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(projects.core.network)
            api(projects.core.utils)
            api(projects.core.uikit)

            api(projects.features.auth.data)
            api(projects.features.auth.domain)
            api(projects.features.auth.ui)

            api(projects.features.profile.data)
            api(projects.features.profile.domain)
            api(projects.features.profile.ui)

            api(projects.features.posts.data)
            api(projects.features.posts.domain)
            api(projects.features.posts.ui)

            api(projects.features.vacancies.data)
            api(projects.features.vacancies.domain)
            api(projects.features.vacancies.ui)

            api(projects.features.fileStorage.data)
            api(projects.features.fileStorage.domain)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.material)
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "kaiyrzhan.de.empath"

    defaultConfig {
        applicationId = "kaiyrzhan.de.empath"
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lint {
        checkReleaseBuilds = false
        checkDependencies = true
    }

    buildFeatures {
        buildConfig = true
    }
}

compose.resources {
    publicResClass = false
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "kaiyrzhan.de.empath.Empath"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Empath"
            packageVersion = "1.0.0"

            windows {
                iconFile.set(project.file("src/desktopMain/resources/icons/ic_app_msi.ico"))
            }

            macOS {
                iconFile.set(project.file("src/desktopMain/resources/icons/ic_app_dmg.icns"))
            }

            linux {
                iconFile.set(project.file("src/desktopMain/resources/icons/ic_app_deb.png"))
            }
        }
    }
}
