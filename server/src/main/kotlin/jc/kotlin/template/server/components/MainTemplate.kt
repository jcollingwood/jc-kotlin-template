package jc.kotlin.template.server.components

import jc.kotlin.template.server.pages.adminPage
import jc.kotlin.template.server.pages.components.componentsPage
import jc.kotlin.template.server.pages.htmxPage
import jc.kotlin.template.server.pages.welcomePage
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.user.UserEntity
import kotlinx.html.*

val navPages = setOf(welcomePage, htmxPage, componentsPage, adminPage)

fun HTML.mainTemplate(user: UserEntity, page: Page, content: FlowContent.() -> Unit) {
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
            navigation(page, filterNavPages(user, navPages))
            content()
        }
    }
}

fun filterNavPages(user: UserEntity, navPages: Set<Page>): Set<Page> {
    return navPages.filter { page ->
        when (page) {
            welcomePage -> true
            htmxPage -> true
            componentsPage -> true
            adminPage -> user.isAdmin
            else -> false
        }
    }.toSet()
}