plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.13.1")
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // logging
    val loggingDep = "io.github.oshai:kotlin-logging-jvm:7.0.7"
    val logbackDep = "ch.qos.logback:logback-classic:1.5.18"
    implementation(loggingDep)
    implementation(logbackDep)

    testImplementation(kotlin("test"))
    testImplementation(loggingDep)
    testImplementation(logbackDep)
}

tasks.test {
    useJUnitPlatform()
}
