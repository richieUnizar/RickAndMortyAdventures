plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(project(":common"))
}
kapt {
    correctErrorTypes = true
}