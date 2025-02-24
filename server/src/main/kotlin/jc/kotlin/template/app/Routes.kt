package jc.kotlin.template.app

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.routing
import jc.kotlin.template.app.auth.UserInfoService
import jc.kotlin.template.app.config.CoreServices
import jc.kotlin.template.app.routes.htmxRoutes
import jc.kotlin.template.app.routes.newRoutes
import jc.kotlin.template.app.routes.welcomeRoute
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

