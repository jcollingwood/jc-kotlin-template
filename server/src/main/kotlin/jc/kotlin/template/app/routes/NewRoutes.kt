package jc.kotlin.template.app.routes

import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import jc.kotlin.template.app.auth.getSession
import jc.kotlin.template.app.components.mainTemplate
import jc.kotlin.template.app.pages.newPage
import jc.kotlin.template.app.pages.newPage2
import jc.kotlin.template.app.pages.newPage3

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
    route(newPage2.path) {
        get {
            getSession(call) ?: return@get

            call.respondHtml {
                mainTemplate(newPage2) {
                    newPage2()
                }
            }
        }
    }
    route(newPage3.path) {
        get {
            getSession(call) ?: return@get

            call.respondHtml {
                mainTemplate(newPage3) {
                    newPage3()
                }
            }
        }
    }
}