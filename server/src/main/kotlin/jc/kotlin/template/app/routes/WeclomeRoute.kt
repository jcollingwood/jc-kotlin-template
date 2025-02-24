package jc.kotlin.template.app.routes

import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import jc.kotlin.template.app.auth.UserInfoService
import jc.kotlin.template.app.auth.UserSession
import jc.kotlin.template.app.auth.getSession
import jc.kotlin.template.app.components.mainTemplate
import jc.kotlin.template.app.pages.welcomePage

fun Route.welcomeRoute(userInfoService: UserInfoService) {
    route(welcomePage.path) {
        get {
            val userSession: UserSession = getSession(call) ?: return@get
            val userInfo = userInfoService.getUserInfo(call, userSession)

            call.respondHtml {
                mainTemplate(welcomePage) {
                    welcomePage(userInfo)
                }
            }
        }
    }
}