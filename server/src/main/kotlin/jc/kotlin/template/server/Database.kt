package jc.kotlin.template.server

import io.ktor.server.application.*
import jc.kotlin.template.server.config.CoreServices
import mu.two.KotlinLogging
import org.flywaydb.core.Flyway

fun Application.configureDatabase(core: CoreServices) {
    val log = KotlinLogging.logger { }

    // Run Flyway migrations at startup
    val dbConfig = core.database.dataSource
    val flyway = Flyway.configure()
        .dataSource(dbConfig)
        // Adjust locations as needed for your environment
        .locations("classpath:db/sqlite/migrations")
        .load()
    val migrationResult = flyway.migrate()
    log.info { "Flyway migration complete: ${migrationResult.migrations.size} migrations applied." }
}