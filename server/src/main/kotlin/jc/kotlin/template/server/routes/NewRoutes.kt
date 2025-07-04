package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.newPage

fun Route.newRoutes() {
    route(newPage.path) {
        get {
            call.respondHtml {
                mainTemplate(newPage) {
                    newPage()
                }
            }
        }
    }
}