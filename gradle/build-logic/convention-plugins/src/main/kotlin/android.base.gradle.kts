import kaiyrzhan.de.empath.gradle.ProjectTargets
import kaiyrzhan.de.empath.gradle.androidConfig
import kaiyrzhan.de.empath.gradle.config.requestedAndroidAbis
import kaiyrzhan.de.empath.gradle.javaVersion
import kaiyrzhan.de.empath.gradle.libs

androidConfig {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        ndk {
            requestedAndroidAbis
                .takeUnless { it.isNullOrEmpty() }
                ?.let { abis: List<String> -> abiFilters.addAll(abis) }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        val javaVersion = libs.javaVersion(ProjectTargets.Android)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

