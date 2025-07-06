plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.flywaydb.flyway") version "11.10.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.50.2.0")
}

flyway {
    // sqlite configuration
    // TODO psql config and flow
    url = "jdbc:sqlite:${rootProject.projectDir}/db/template_db.db"
    locations = arrayOf("filesystem:flyway/sqlite")
    cleanDisabled = true
}
