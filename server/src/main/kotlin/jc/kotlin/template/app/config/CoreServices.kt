package jc.kotlin.template.app.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * to be shared and used by all/most services
 *
 * using this in place of DI atm
 */
data class CoreServices(
    val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    },
    val jsonParser: Json = Json { ignoreUnknownKeys = true }
)