package jc.kotlin.template.app.pages

import jc.kotlin.template.app.Page
import kotlinx.html.FlowContent
import kotlinx.html.h1
import kotlinx.html.p

val newPage = Page("/new", "New Page")
fun FlowContent.newPage() {
    h1(classes = "text-2xl") { +"New Page" }
    p { +"This is a new page" }
}

val newPage2 = Page("/new2", "New Page 2")
fun FlowContent.newPage2() {
    h1(classes = "text-2xl") { +"New Page 2" }
    p { +"This is a new page" }
}

val newPage3 = Page("/new3", "New Page 3")
fun FlowContent.newPage3() {
    h1(classes = "text-2xl") { +"New Page 3" }
    p { +"This is a new page" }
}