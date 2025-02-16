plugins {
    `kotlin-dsl`
}

group = "kaiyrzhan.de.empath.gradle"

dependencies {
    implementation(libs.gradleplugins.android)
    implementation(libs.gradleplugins.kotlin)
    implementation(libs.gradleplugins.compose)
    implementation(libs.gradleplugins.composeCompiler)
    implementation(libs.gradleplugins.kotlinxSerialization)
    implementation(libs.gradleplugins.ktorfit)
    implementation(libs.gradleplugins.detekt)

    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

java {
    targetCompatibility = JavaVersion.VERSION_21
    sourceCompatibility = JavaVersion.VERSION_21
}
