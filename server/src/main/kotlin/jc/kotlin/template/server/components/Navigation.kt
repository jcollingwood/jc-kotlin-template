package jc.kotlin.template.server.components

import jc.kotlin.template.server.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.nav

// TODO nav not responsive at all yet
fun FlowContent.navigation(currentPage: Page, navPages: Set<Page>) {
    nav {
        classes = setOf(" min-w-2xl", "flex", "flex-row", "divide-x-2")

        val defaultClasses = setOf(
            "p-1",
            "px-8",
            "border-b-2",
            "border-gray-200",
            "hover:bg-gray-100",
            "active:bg-gray-200"
        )
        val isCurrentClasses = setOf("font-bold", "border-gray-400")

        navPages.map {
            val isCurrent = currentPage == it
            a(it.path) {
                classes = if (isCurrent) defaultClasses + isCurrentClasses
                else defaultClasses

                +it.title
            }
        }
    }
}