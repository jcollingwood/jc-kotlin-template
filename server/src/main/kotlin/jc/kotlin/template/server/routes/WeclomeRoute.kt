package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.auth.SESSION_DATA
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.welcomePage
import jc.kotlin.template.server.session.SessionService
import jc.kotlin.template.server.session.getUser

fun Route.welcomeRoute(sessionService: SessionService) {
    route(welcomePage.path) {
        get {
            val session = call.attributes[SESSION_DATA]
            val user = getUser(call, sessionService)

            call.respondHtml {
                mainTemplate(user, welcomePage) {
                    welcomePage(user, session)
                }
            }
        }
    }
}