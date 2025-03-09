package jc.kotlin.template.server.routes

import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import jc.kotlin.template.server.auth.getSession
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.buttonComponent
import jc.kotlin.template.server.pages.cardComponent
import jc.kotlin.template.server.pages.componentsPage
import jc.kotlin.template.server.pages.formComponent
import kotlinx.html.body
import kotlinx.html.p

fun Route.componentsRoutes() {
    route(componentsPage.path) {
        get {
            getSession(call) ?: return@get

            call.respondHtml {
                mainTemplate(componentsPage) {
                    componentsPage()
                }
            }
        }
        get("button") {
            getSession(call) ?: return@get
            call.respondHtml {
                body {
                    buttonComponent()
                }
            }
        }
        route("form") {
            get {
                getSession(call) ?: return@get
                call.respondHtml {
                    body {
                        formComponent()
                    }
                }
            }
            post("submit") {
                getSession(call) ?: return@post
                call.respondHtml {
                    body {
                        p {
                            // TODO handle form submission and display results
                            +"Form submitted"
                        }
                    }
                }
            }
        }
        get("card") {
            getSession(call) ?: return@get
            call.respondHtml {
                body {
                    cardComponent()
                }
            }
        }
    }
}