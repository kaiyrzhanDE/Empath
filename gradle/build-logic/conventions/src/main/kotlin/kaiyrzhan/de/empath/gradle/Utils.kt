package kaiyrzhan.de.empath.gradle

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope


private const val IMPLEMENTATION = "implementation"
internal fun DependencyHandlerScope.implementation(dependency: Provider<MinimalExternalModuleDependency>){
    IMPLEMENTATION(dependency)
}

const val DEBUG_IMPLEMENTATION = "debugImplementation"
internal fun DependencyHandlerScope.debugImplementation(dependency: String){
    DEBUG_IMPLEMENTATION(dependency)
}

/**
 * Apply plugin if it is not applied yet
 */
internal fun PluginContainer.applyIfNeeded(id: String): Boolean {
    if (hasPlugin(id)) return false
    apply(id)
    return true
}
