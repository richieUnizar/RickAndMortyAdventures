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
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    // Mockk
    testImplementation(libs.mockk)

    // JUnit 5
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // Coroutines test library
    testImplementation(libs.corroutine.test)

    implementation(project(":common"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}