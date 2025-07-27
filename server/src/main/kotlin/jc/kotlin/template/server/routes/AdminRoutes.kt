package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.adminPage
import jc.kotlin.template.server.session.AdminSessionValidationPlugin
import jc.kotlin.template.server.session.SessionService
import jc.kotlin.template.server.session.getUser

fun Route.adminRoutes(sessionService: SessionService) {
    route("/") {
        install(AdminSessionValidationPlugin) {
            this.sessionService = sessionService
        }
        route(adminPage.path) {
            get {
                val user = getUser(call, sessionService)
                call.respondHtml {
                    mainTemplate(user, adminPage) {
                        adminPage(user)
                    }
                }
            }
        }
    }
}