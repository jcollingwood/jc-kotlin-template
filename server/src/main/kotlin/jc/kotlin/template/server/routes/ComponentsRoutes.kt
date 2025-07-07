package jc.kotlin.template.server.routes

import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import jc.kotlin.template.server.components.mainTemplate
import jc.kotlin.template.server.pages.components.*
import kotlinx.html.body

fun Route.componentsRoutes() {
    route(componentsPage.path) {
        get {
            call.respondHtml {
                mainTemplate(componentsPage) {
                    componentsPage()
                }
            }
        }
        get("button") {
            call.respondHtml {
                body {
                    buttonComponent()
                }
            }
        }
        route("form") {
            get {
                call.respondHtml {
                    body {
                        formComponent()
                    }
                }
            }
            post("validate") {
                val input = call.pathParameters["input"] ?: ""
                val formParameters = call.receiveParameters()

                call.respondHtml {
                    body {
                        formValidation(input, formParameters)
                    }
                }
            }
            post("submit") {
                val formParameters = call.receiveParameters()

                call.respondHtml {
                    body {
                        formSubmission(formParameters)
                    }
                }
            }
        }
        get("card") {
            call.respondHtml {
                body {
                    cardComponent()
                }
            }
        }
        route("modal") {
            get {
                call.respondHtml {
                    body {
                        modalComponents()
                    }
                }
            }
            route("content") {
                get("{id}") {
                    call.respondHtml {
                        body {
                            modalContent(call.parameters["id"].toString())
                        }
                    }
                }
            }
        }
        route("peek") {
            get {
                call.respondHtml {
                    body {
                        peekComponent()
                    }
                }
            }
            get("content") {
                call.respondHtml {
                    body {
                        peekContent("1")
                    }
                }
            }
        }
    }
}