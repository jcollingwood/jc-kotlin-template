package jc.kotlin.template.server.routes

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.routing
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.config.CoreServices
import java.io.File

data class Page(val path: String, val title: String)

fun Application.appRoutes(core: CoreServices) {
    val userInfoService = UserInfoService(core)
    routing {
        // static directory route relative to project root dir, should pull in tailwind css
        staticFiles("/static", File("server/src/main/resources/static"))

        welcomeRoute(userInfoService)
        htmxRoutes()
        newRoutes()
    }
}

