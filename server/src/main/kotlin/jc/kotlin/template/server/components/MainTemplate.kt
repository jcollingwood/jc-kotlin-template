package jc.kotlin.template.server.components

import jc.kotlin.template.server.pages.htmxPage
import jc.kotlin.template.server.pages.newPage
import jc.kotlin.template.server.pages.welcomePage
import jc.kotlin.template.server.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.main

val navPages = setOf(welcomePage, htmxPage, newPage)

fun HTML.mainTemplate(page: Page, content: FlowContent.() -> Unit) {
    appHead(page.title)
    body {
        main {
            classes = setOf(
                "font-inter",
                "flex",
                "flex-col",
                "h-full",
                "w-screen",
                "items-center",
                "px-4",
                "pb-6"
            )
            navigation(page, navPages)
            content()
        }
    }
}