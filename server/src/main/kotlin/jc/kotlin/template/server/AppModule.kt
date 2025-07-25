package jc.kotlin.template.server

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.request.*
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.auth.authModule
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.configureSessionPruning
import jc.kotlin.template.server.config.errorHandler
import jc.kotlin.template.server.routes.appRoutes
import jc.kotlin.template.server.session.SessionService
import org.slf4j.event.Level

fun Application.appModule(core: CoreServices, userInfoService: UserInfoService, sessionService: SessionService) {
    install(CallLogging) {
        level = Level.DEBUG
        filter { call ->
            // ignore static resources and preflight requests
            !call.request.uri.startsWith("/static/") && call.request.httpMethod.value != "OPTIONS"
        }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            "$httpMethod ${call.request.uri} - $status (Headers ${
                call.request.headers.entries().joinToString(" | ") { "${it.key}: ${it.value}" }
            })"
        }
    }
    // Install the X-Forwarded-For header plugin to handle reverse proxy setups
    install(XForwardedHeaders)
    errorHandler()
    configureDatabase(core)
    configureSessionPruning(sessionService)
    authModule(
        core = core,
        userInfoService = userInfoService,
        sessionService = sessionService
    )
    appRoutes(
        core = core,
        userInfoService = userInfoService,
        sessionService = sessionService
    )
}
