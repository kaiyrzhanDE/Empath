import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.detektConfig
import kaiyrzhan.de.empath.gradle.libs
import io.gitlab.arturbosch.detekt.Detekt

plugins.applyIfNeeded(libs.plugins.detekt.get().pluginId)

detektConfig {
    // Version of detekt that will be used. When unspecified the latest detekt
    // version found will be used. Override to stay on the same version.
    toolVersion = libs.versions.detekt.get()

    // The directories where detekt looks for source files.
    // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
//    source.setFrom()

    // Define the detekt configuration(s) you want to use.
    // Defaults to the default detekt configuration.
    config.setFrom(
        File(rootProject.rootDir, "config/detekt/detekt.yml"),
        File(rootProject.rootDir, "config/detekt/detekt-compose.yml"),
    )

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = false

    // Turns on all the rules. `false` by default.
    allRules = false

    // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
    baseline = file("detekt-baseline.xml")

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false

    // Adds debug output during task execution. `false` by default.
    debug = false

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = false

    parallel = true
}

tasks.withType<Detekt>().configureEach {
    setSource(projectDir)
    include("**/src/*/kotlin/**/*.kt") // Include all Kotlin source files from all directories

    exclude(
        "gradle/conventions", // Don't analyze Convention Plugin for the project
        "**/build/generated/" // Exclude all generated files from Gradle Build directory
    )

    with(this.project) {
        reports {
            xml.apply {
                isEnabled = true
                outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.xml"))
            }

            txt.apply {
                isEnabled = true
                outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.txt"))
            }

            html.apply {
                isEnabled = true
                outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.html"))
            }

            sarif.apply {
                isEnabled = false
                outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.sarif"))
            }

            md.apply {
                // Required to fail Gradle task
                isEnabled = true
                outputLocation.set(layout.buildDirectory.file("reports/detekt/detekt.md"))
            }
        }
    }
}

dependencies.add("detektPlugins", libs.detektPlugin.nlopez.composeRules)

