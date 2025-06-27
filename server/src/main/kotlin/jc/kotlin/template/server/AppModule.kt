package jc.kotlin.template.server

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.forwardedheaders.ForwardedHeaders
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import jc.kotlin.template.server.auth.authModule
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.errorHandler
import jc.kotlin.template.server.routes.appRoutes
import org.slf4j.event.Level

fun Application.appModule(core: CoreServices) {
    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            // ignore static resources and preflight requests
            !call.request.uri.startsWith("/static/") && call.request.httpMethod.value != "OPTIONS"
        }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            "$httpMethod ${call.request.uri} - $status"
        }
    }
    install(ForwardedHeaders)
    errorHandler()
    configureDatabase()
    authModule(core)
    appRoutes(core)
}
