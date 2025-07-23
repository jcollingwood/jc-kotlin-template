package jc.kotlin.template.server.config

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond


private val log = KotlinLogging.logger {}

// error handler to prevent unhandled exceptions from getting returned
fun Application.errorHandler() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            log.error(cause) { "unhandled exception" }
            call.respond(HttpStatusCode.InternalServerError, "oops...")
        }
    }
}