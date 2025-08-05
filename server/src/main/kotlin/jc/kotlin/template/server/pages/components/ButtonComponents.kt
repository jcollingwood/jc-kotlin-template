package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.utility.Color
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
            button {
                btn()
                btnAccent()

                +"Primary Mint"
            }
            button {
                val props = BtnProps(
                    accentColor = Color.Peach,
                    type = BtnType.Primary
                )
                btn(props)
                btnAccent(props)

                +"Primary Peach"
            }
            button {
                val props = BtnProps(
                    accentColor = Color.Purple,
                    type = BtnType.Primary
                )
                btn(props)
                btnAccent(props)

                +"Primary Purple"
            }
            button {
                val props = BtnProps(
                    accentColor = Color.Mint,
                    type = BtnType.Secondary
                )
                btn(props)
                btnAccent(props)

                +"Secondary Mint"
            }
            button {
                val props = BtnProps(
                    accentColor = Color.Peach,
                    type = BtnType.Secondary
                )
                btn(props)
                btnAccent(props)

                +"Secondary Peach"
            }
            button {
                val props = BtnProps(
                    accentColor = Color.Purple,
                    type = BtnType.Secondary
                )
                btn(props)
                btnAccent(props)

                +"Secondary Purple"
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