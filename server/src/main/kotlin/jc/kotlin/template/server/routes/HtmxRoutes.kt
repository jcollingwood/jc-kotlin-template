package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.genericHtmxSection
import jc.kotlin.template.server.pages.htmxPage
import kotlinx.html.body

fun Route.htmxRoutes() {
    route(htmxPage.path) {
        get {
            call.respondHtml {
                mainTemplate(htmxPage) {
                    htmxPage()
                }
            }
        }
        get("/section/{sectionNum}") {
            val sectionNum = call.parameters["sectionNum"]?.toInt() ?: 1
            // used to simulate slow page load for example
            val delay = call.queryParameters["ms_delay"]?.toInt() ?: 0
            Thread.sleep(delay.toLong())

            val color = call.queryParameters["color"] ?: "gray"

            call.respondHtml {
                body {
                    genericHtmxSection(sectionNum, color)
                }
            }
        }
    }

}