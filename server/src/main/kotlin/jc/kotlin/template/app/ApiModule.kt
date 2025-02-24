package jc.kotlin.template.app

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
)

val testUsers = listOf(
    UserDto(1, "Luna"),
    UserDto(2, "Dany"),
    UserDto(3, "Hodor"),
)

/**
 * very basic API module setup to handle json http requests
 */
fun Application.apiModule() {
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        // depends on use case of API what CORS options to allow
        allowMethod(HttpMethod.Get)
    }
    install(Compression) {
        gzip()
    }
    routing {
        route("/api") {
            get("/users") {
                call.respond(testUsers)
            }
            get("/users/{userId}") {
                val playerId = call.parameters["userId"]?.toInt() ?: error("Invalid user id")
                call.respond(testUsers.find { it.id == playerId } ?: error("User not found"))
            }
        }
    }
}
