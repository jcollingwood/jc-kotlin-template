package jc.kotlin.template.server.routes

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.config.CoreServices

data class Page(val path: String, val title: String)

fun Application.appRoutes(core: CoreServices) {
    val userInfoService = UserInfoService(core)
    routing {
        staticResources("/static", "static")

        welcomeRoute(userInfoService)
        htmxRoutes()
        componentsRoutes()
        newRoutes()
    }
}

