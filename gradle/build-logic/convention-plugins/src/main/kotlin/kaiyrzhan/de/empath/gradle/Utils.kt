package kaiyrzhan.de.empath.gradle

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

internal const val DESKTOP = "desktop"

private const val IMPLEMENTATION = "implementation"
internal fun DependencyHandlerScope.implementation(dependencyNotation: Provider<MinimalExternalModuleDependency>){
    IMPLEMENTATION(dependencyNotation)
}

private const val DEBUG_IMPLEMENTATION = "debugImplementation"
internal fun DependencyHandlerScope.debugImplementation(dependencyNotation: String){
    DEBUG_IMPLEMENTATION(dependencyNotation)
}

/**
 * Apply plugin if it is not applied yet
 */
/**
 * Apply plugin if it is not applied yet
 */
internal fun PluginContainer.applyIfNeeded(
    id: String,
    vararg ids: String,
): Boolean {
    if (hasPlugin(id) || ids.any(::hasPlugin)) return false

    apply(id)
    return true
}
