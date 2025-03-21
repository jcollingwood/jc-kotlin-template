package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.auth.getSession
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.components.*
import kotlinx.html.body

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
            post("validate") {
                getSession(call) ?: return@post

                val input = call.pathParameters["input"] ?: ""
                val formParameters = call.receiveParameters()

                call.respondHtml {
                    body {
                        formValidation(input, formParameters)
                    }
                }
            }
            post("submit") {
                getSession(call) ?: return@post

                val formParameters = call.receiveParameters()

                call.respondHtml {
                    body {
                        formSubmission(formParameters)
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