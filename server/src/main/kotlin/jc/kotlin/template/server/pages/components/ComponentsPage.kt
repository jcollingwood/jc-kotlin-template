package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.routes.Page
import kotlinx.html.*

data class Component(
    val name: String,
    val route: String
)

val componentsPage = Page("/components", "Components")
fun FlowContent.componentsPage() {
    h1(classes = "text-2xl") { +"Components" }
    div {
        classes = setOf("flex", "flex-col", "w-full", "items-center", "mt-4", "gap-4")
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-5", "gap-4", "items-center")
            val componentButtons = listOf(
                Component("Button", "/components/button"),
                Component("Form", "/components/form"),
                Component("Card", "/components/card"),
                Component("Modal", "/components/modal"),
                Component("Peek", "/components/peek")
            )
            componentButtons.map { component ->
                jcButton {
                    hxGet(component.route)
                    hxTarget("#component-area")
                    hxSwap("innerHTML")
                    +component.name
                }
            }
        }
        div {
            id = "component-area"
            classes = cardStyles + setOf("!w-1/2", "p-4", "py-4")
            p {
                classes = setOf("text-center")
                +"Select a component to test"
            }
        }
    }
}
