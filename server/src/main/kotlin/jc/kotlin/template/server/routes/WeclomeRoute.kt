package jc.kotlin.template.server.routes

import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.auth.UserSession
import jc.kotlin.template.server.auth.getSession
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.welcomePage

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