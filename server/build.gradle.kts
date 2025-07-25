import com.github.gradle.node.npm.task.NpxTask

val ktorVersion = "3.2.2"

plugins {
    id("jc.kotlin.template.kotlin-application-conventions")
    id("io.ktor.plugin") version "3.2.2"
    // node required for tailwind task
    id("com.github.node-gradle.node") version "7.1.0"
    kotlin("plugin.serialization") version "2.2.0"
}

application {
    mainClass.set("jc.kotlin.template.server.ServerMainKt")
}

dependencies {
    // ktor dependencies
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-compression:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-forwarded-header:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    // http client deps
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    // html/css dsl
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")

    // database deps
    // db connection and pooling
    implementation("com.zaxxer:HikariCP:6.3.0")
    // For PostgreSQL
    implementation("org.postgresql:postgresql:42.7.7")
    // For SQLite
    implementation("org.xerial:sqlite-jdbc:3.50.3.0")
    // Add Flyway for runtime migrations
    implementation("org.flywaydb:flyway-core:11.10.3")
}

node {
    // need to set download=true unless you want to use locally installed node
    download.set(true)
    version.set("22.17.1")
}

// tailwind task crawls source kt files and generates styles.css with necessary css classes
tasks.register<NpxTask>("tailwind") {
    // TODO tailwind version 4+ broke this task... figure out why and upgrade
    command.set("tailwindcss@3.4.17")
    args.addAll("-o", "src/main/resources/static/styles.css")
}

// runs tailwind task on build, regenerating styles.css
tasks.named("processResources") {
    dependsOn("tailwind")
}
