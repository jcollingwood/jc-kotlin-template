package jc.kotlin.template.server.pages

import io.ktor.http.Parameters
import jc.kotlin.template.server.components.ButtonColor
import jc.kotlin.template.server.components.IconPosition
import jc.kotlin.template.server.components.cardStyles
import jc.kotlin.template.server.components.hxGet
import jc.kotlin.template.server.components.hxPost
import jc.kotlin.template.server.components.hxSwap
import jc.kotlin.template.server.components.hxSwapOob
import jc.kotlin.template.server.components.hxSync
import jc.kotlin.template.server.components.hxTarget
import jc.kotlin.template.server.components.hxTrigger
import jc.kotlin.template.server.components.iconButtonStyles
import jc.kotlin.template.server.components.iconSpan
import jc.kotlin.template.server.components.inputStyles
import jc.kotlin.template.server.components.jcButton
import jc.kotlin.template.server.components.jcCard
import jc.kotlin.template.server.components.jcIconButton
import jc.kotlin.template.server.components.required
import jc.kotlin.template.server.routes.Page
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.dd
import kotlinx.html.div
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.hr
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.span

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

fun FlowContent.formComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Form"
        }
        p {
            +"Sample form to showcase HTMX form submission and it's bells and whistles"
        }
        form {
            hxPost("/components/form/submit")
            hxTarget("#form-result")
            hxSwap("outerHTML")

            classes = setOf("flex", "flex-col", "gap-4")

            val formFieldStyles = setOf("flex", "flex-col", "gap-2")

            span {
                classes = formFieldStyles
                label {
                    +"First Input"
                }
                input {
                    classes = inputStyles

                    type = InputType.text
                    name = "first_input"

                    placeholder = "Placeholder"
                }
            }

            span {
                classes = formFieldStyles
                label {
                    required()
                    +"Second Input w/ Validations"
                }
                input {
                    hxPost("/components/form/validate")
                    hxTrigger("change")
                    hxSwap()
                    hxSync()

                    classes = inputStyles

                    type = InputType.text
                    name = "second_input"
                    required = true

                    placeholder = "Must be between 3 and 10 characters"
                }
                p {
                    id = "second_input_error"
                }
            }

            // TODO radio button

            // TODO checkbox

            // TODO select

            // TODO textarea

            // TODO disable button until valid form
            span {
                classes = setOf("mt-4")
                jcButton(iconButtonStyles + setOf("pr-10")) {
                    type = ButtonType.submit

                    iconSpan(
                        icon = "progress_activity",
                        extraClasses = setOf("htmx-indicator", "animate-spin")
                    )
                    +"Submit"
                }
            }
        }
        div {
            id = "form-result"
        }
    }
}

fun FlowContent.formSubmission(parameters: Parameters) {
    val firstInput = parameters["first_input"].toString()
    val secondInput = parameters["second_input"].toString()

    // simulating longer submission delay
    Thread.sleep(2000)

    val errorMap = validateForm(parameters)

    div {
        id = "form-result"

        hr("my-4")

        // show results if no errors
        if (errorMap.values.all { it == null }) {
            section("flex flex-col gap-2") {
                h3("text-lg") {
                    +"Form submitted!"
                }
                dl("grid grid-cols-2 gap-2") {
                    dt("font-bold") { +"First Input:" }
                    dd { +firstInput }
                    dt("font-bold") { +"Second Input:" }
                    dd { +secondInput }
                }
            }
        } else {
            // else show errors
            p("text-red-600") {
                +"Form has errors"
            }
            showValidationErrors(errorMap)
        }
    }
}

fun validateForm(parameters: Parameters): Map<String, String?> {
    val errorMap = mutableMapOf<String, String?>()

    val secondInput = parameters["second_input"].toString()
    errorMap["second_input"] = if (secondInput.isEmpty()) {
        "This field is required"
    } else if (secondInput.length < 3) {
        "This field must be at least 3 characters"
    } else if (secondInput.length > 10) {
        "This field must be less than 10 characters"
    } else {
        null
    }

    return errorMap
}

fun FlowContent.showValidationErrors(errorMap: Map<String, String?>) {
    errorMap.forEach { (key, value) ->
        val formId = "${key}_error"
        value?.let {
            p {
                hxSwapOob()
                id = formId
                classes = setOf("text-red-600", "text-sm")
                +it
            }
        } ?: p {
            hxSwapOob()
            id = formId
        }
    }
}

fun FlowContent.formValidation(parameters: Parameters) {
    val errorMap = validateForm(parameters)
    showValidationErrors(errorMap)
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