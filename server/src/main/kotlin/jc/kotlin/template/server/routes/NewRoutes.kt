package jc.kotlin.template.server.routes

import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import jc.kotlin.template.server.auth.getSession
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.newPage

fun Route.newRoutes() {
    route(newPage.path) {
        get {
            getSession(call) ?: return@get

            call.respondHtml {
                mainTemplate(newPage) {
                    newPage()
                }
            }
        }
    }
}