package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*

data class Component(
    val name: String,
    val route: String
)

val componentsPage = Page("/components", "Components")
fun FlowContent.componentsPage() {
    div {
        classes = setOf("flex", "flex-col", "gap-6", "w-full", "max-w-2xl", "font-xl")

        h1(classes = "header text-xl relative") {
            titleAccent()
            +"Components"
        }
        div {
            classes = setOf("flex", "flex-col", "w-full", "mt-4", "gap-4")
            div {
                classes = setOf("grid", "grid-cols-1", "sm:grid-cols-5", "gap-4", "items-center", "pb-4")
                val componentButtons = listOf(
                    Component("Button", "/components/button"),
                    Component("Form", "/components/form"),
                    Component("Card", "/components/card"),
                    Component("Modal", "/components/modal"),
                    Component("Peek", "/components/peek")
                )
                componentButtons.map { component ->
                    button {
                        hxGet(component.route)
                        hxTarget("#component-area")
                        hxSwap("innerHTML")

                        val props = BtnProps(
                            accentColor = Color.entries.random(),
                            type = BtnType.Secondary,
                        )
                        btn(props)
                        btnAccent(props)

                        +component.name
                    }
                }
            }
            section {
                id = "component-area"
                classes = setOf("max-w-full", "p-4", "py-4")
                card()
                cardAccent()
                p {
                    classes = setOf("text-center")
                    +"Select a component to test"
                }
            }
        }
    }
}
