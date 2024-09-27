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
    // Rest - retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(project(":common"))
}

kapt {
    correctErrorTypes = true
}