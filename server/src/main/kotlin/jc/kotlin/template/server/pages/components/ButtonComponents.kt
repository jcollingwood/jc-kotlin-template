package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import kotlinx.html.*

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
            jcButton(
                iconButtonStyles
            ) {
                +"Loading Spinner"
                iconSpan("progress_activity", setOf("animate-spin"))
            }
            jcButton(
                extraClasses = setOf("animate-bounce")

            ) {
                +":)"
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