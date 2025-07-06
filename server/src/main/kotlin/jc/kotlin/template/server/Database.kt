package jc.kotlin.template.server

import io.ktor.server.application.Application
import jc.kotlin.template.server.config.CoreServices
import mu.two.KotlinLogging

fun Application.configureDatabase(core: CoreServices) {
    val log = KotlinLogging.logger { }
    core.database.useConnection { conn ->
        // log flyway migrations
        conn.prepareStatement(
            """
              SELECT version, description, installed_on, state
              FROM flyway_schema_history
              ORDER BY installed_on DESC; 
       """.trimIndent()
        )
            .use { stmt ->
                val result = stmt.executeQuery()
                while (result.next()) {
                    val version = result.getString("version")
                    val description = result.getString("description")
                    val installedOn = result.getTimestamp("installed_on")
                    val state = result.getString("state")
                    log.info("Flyway migration: $version - $description on $installedOn with state $state")
                }
            }
    }
}