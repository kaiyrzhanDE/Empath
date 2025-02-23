package kaiyrzhan.de.empath.gradle.config

import org.gradle.api.Project
import java.util.Properties

internal data class ProjectConfigParam(
    val cmdParamName: String,
    val androidLocalPropertyParamName: String? = null,
    val envParamName: String? = null,
    val defaultValue: String? = null,
)

@Suppress("ktlint:standard:function-signature")
internal fun Project.readConfigParam(param: ProjectConfigParam): String? = with(param) {
    return rootProject.findProperty(cmdParamName) as String? // 1. Gradle property
        ?: androidLocalPropertyParamName?.let(::readFromLocalProperties) // 2. From local.properties
        ?: envParamName?.let(System::getenv) // 3. Environment variable
        ?: defaultValue
}

internal fun Project.readFromLocalProperties(key: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) return null

    val localProperties = Properties().apply {
        localPropertiesFile.inputStream().use(::load)
    }
    return localProperties.getProperty(key)
}
