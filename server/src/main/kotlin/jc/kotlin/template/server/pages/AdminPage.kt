package jc.kotlin.template.server.pages

import jc.kotlin.template.server.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.h1
import kotlinx.html.p

val adminPage = Page("/admin", "Admin Page")
fun FlowContent.adminPage() {
    h1(classes = "text-2xl") { +"Admin Page" }
    p { +"TODO add admin-y things" }
}