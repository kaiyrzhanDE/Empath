import kaiyrzhan.de.empath.gradle.applyIfNeeded
import kaiyrzhan.de.empath.gradle.implementation
import kaiyrzhan.de.empath.gradle.javaVersion
import kaiyrzhan.de.empath.gradle.jvmTarget
import kaiyrzhan.de.empath.gradle.kotlinJvmCompilerOptions
import kaiyrzhan.de.empath.gradle.libs

plugins.apply("android.base")
if (plugins.hasPlugin(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId)) {
    plugins.applyIfNeeded(libs.plugins.jetbrains.kotlin.android.get().pluginId)
}

project.dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core)
    implementation(libs.androidx.annotation)
}

kotlinJvmCompilerOptions {
    jvmTarget.set(libs.jvmTarget())
    freeCompilerArgs.add("-Xjdk-release=${libs.javaVersion()}")
}
