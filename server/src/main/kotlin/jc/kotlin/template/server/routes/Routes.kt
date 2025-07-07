package jc.kotlin.template.server.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.session.SessionService
import jc.kotlin.template.server.session.SessionValidationPlugin

data class Page(val path: String, val title: String)

fun Application.appRoutes(core: CoreServices, userInfoService: UserInfoService, sessionService: SessionService) {
    routing {
        staticResources("/static", "static")

        route("/") {
            // validate session for all routes under this route
            install(SessionValidationPlugin) {
                this.sessionService = sessionService
                redirectOnFailure = true
                requireValidToken = true
            }

            welcomeRoute(userInfoService)
            htmxRoutes()
            componentsRoutes()
            newRoutes()
        }
    }
}

