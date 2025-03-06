package jc.kotlin.template.server.config

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import mu.two.KotlinLogging


private val log = KotlinLogging.logger {}

// error handler to prevent unhandled exceptions from getting returned
fun Application.errorHandler() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            log.error("unhandled exception", cause)
            call.respond(HttpStatusCode.InternalServerError, "oops...")
        }
    }
}