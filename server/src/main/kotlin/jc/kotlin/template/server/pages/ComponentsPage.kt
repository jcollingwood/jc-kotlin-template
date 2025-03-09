package jc.kotlin.template.server.pages

import jc.kotlin.template.server.components.ButtonColor
import jc.kotlin.template.server.components.IconPosition
import jc.kotlin.template.server.components.cardStyles
import jc.kotlin.template.server.components.hxGet
import jc.kotlin.template.server.components.hxPost
import jc.kotlin.template.server.components.hxSwap
import jc.kotlin.template.server.components.hxTarget
import jc.kotlin.template.server.components.inputStyles
import jc.kotlin.template.server.components.jcButton
import jc.kotlin.template.server.components.jcCard
import jc.kotlin.template.server.components.jcIconButton
import jc.kotlin.template.server.routes.Page
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p

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
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-3", "gap-4", "items-center")
            val componentButtons = listOf(
                Component("Button", "/components/button"),
                Component("Form", "/components/form"),
                Component("Card", "/components/card")
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

fun FlowContent.buttonComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Button"
        }
        p {
            +"""
            Default button styling can be configured in one spot. 
            Color overrides are available via a typed object. 
            Basic icon configuration either before or after the text is supported. 
            Additionally, extra classes are supported as additional arguments to accommodate button styling.
            """
        }
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-2", "w-full", "items-center", "gap-4")
            jcButton {
                +"Default"
            }
            jcButton(
                buttonColor = ButtonColor(
                    hoverText = "hover:text-white",
                    color = "bg-red-100",
                    hover = "hover:bg-red-400",
                    active = "active:bg-red-500",
                    border = "border-red-400",
                    activeBorder = "active:border-red-300"
                )
            ) {
                +"Snazzy Styles"
            }
            jcButton(
                buttonColor = ButtonColor(
                    hover = "hover:bg-green-100",
                    active = "active:bg-green-200",
                )
            ) {

                +"Green Hover"
            }
            jcButton(setOf("font-bold")) {
                +"Bold"
            }
            jcButton(setOf("!rounded-full")) {
                +"Rounded"
            }
            jcButton(
                setOf("rounded-none"),
                buttonColor = ButtonColor(
                    text = "text-white",
                    hoverText = "hover:text-white",
                    color = "bg-gray-500",
                    hover = "hover:bg-gray-600",
                    active = "active:bg-black"
                )
            ) {
                +"Serious"
            }
            jcButton(icon = "add") {
                +"Icon"
            }
            jcButton(icon = "chevron_right", iconPosition = IconPosition.AFTER) {
                +"Alt Icon"
            }
            jcButton(setOf("!shadow-lg")) {
                +"Extra Shadow"
            }
            jcButton(
                setOf("border-none"),
                buttonColor = ButtonColor(
                    text = "text-white",
                    hoverText = "hover:text-white",
                    color = "bg-blue-500",
                    hover = "hover:bg-blue-600",
                    active = "active:bg-blue-700"
                )
            ) {
                +"Flat"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-2")
                p { +"Icon Buttons" }
                div {
                    classes = setOf("flex", "gap-2")
                    jcIconButton(
                        icon = "add", buttonColor = ButtonColor(
                            color = "bg-green-100",
                            hover = "hover:bg-green-200",
                            active = "active:bg-green-300",
                        )
                    ) {}
                    jcIconButton("star") {}
                    jcIconButton(
                        icon = "bolt",
                        buttonColor = ButtonColor(
                            text = "text-white",
                            hoverText = "hover:text-white",
                            color = "bg-yellow-500",
                            hover = "hover:bg-yellow-600",
                            active = "active:bg-yellow-700",
                            border = "border-white",
                            activeBorder = "active:border-white",
                        )
                    ) {}
                }
            }
        }
    }
}

fun FlowContent.formComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Form"
        }
        p {
            +"This is an input... nothing crazy here... yet."
        }
        form {
            hxPost("/components/form/submit")
            hxTarget("#form-result")
            hxSwap("outerHTML")

            classes = setOf("flex", "flex-col", "gap-3")

            label {
                +"Input Label"
            }
            input {
                classes = inputStyles
                placeholder = "Placeholder"
            }

            // TODO radio button

            // TODO checkbox

            // TODO select

            // TODO textarea

            // TODO disable button until valid form
            // TODO loading spinner
            jcButton {
                type = ButtonType.submit
                +"Submit"
            }
        }
        p {
            id = "form-result"
        }
    }
}

fun FlowContent.cardComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Card"
        }
        p {
            +"""
            Also boring... 
            basic card available as component or basic styles constant is also a viable solution.
            Cards by default will take up the entire width given by their parent.
            """
        }
        jcCard {
            +"This is a standard card... cardception..."
        }
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-2", "gap-4")
            jcCard { +"These" }
            jcCard { +"Cards" }
            jcCard { +"Are" }
            jcCard { +"In" }
            jcCard { +"A" }
            jcCard { +"Grid" }
        }
    }
}