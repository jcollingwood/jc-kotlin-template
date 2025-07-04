package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.auth.SESSION_DATA
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.welcomePage

fun Route.welcomeRoute(userInfoService: UserInfoService) {
    route(welcomePage.path) {
        get {
            val session = call.attributes[SESSION_DATA]
            val userInfo = userInfoService.getUserInfoFromSession(call, session)

            call.respondHtml {
                mainTemplate(welcomePage) {
                    welcomePage(userInfo)
                }
            }
        }
    }
}