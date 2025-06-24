package jc.kotlin.template.server.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * to be shared and used by all/most services
 *
 * using this in place of DI atm
 */
data class CoreServices(
    val httpClient: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(ContentNegotiation) {
            json()
        }
    },
    val jsonParser: Json = Json { ignoreUnknownKeys = true }
)