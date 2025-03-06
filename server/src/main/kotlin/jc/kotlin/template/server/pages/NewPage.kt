package jc.kotlin.template.server.pages

import jc.kotlin.template.server.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.h1
import kotlinx.html.p

val newPage = Page("/new", "New Page")
fun FlowContent.newPage() {
    h1(classes = "text-2xl") { +"New Page" }
    p { +"This is a new page" }
}