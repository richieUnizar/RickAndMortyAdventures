plugins {
    id("java-library")
    id("kotlin-kapt")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation(project(":common"))
}

kapt {
    correctErrorTypes = true
}