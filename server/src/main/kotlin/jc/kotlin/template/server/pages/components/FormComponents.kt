package jc.kotlin.template.server.pages.components

import io.ktor.http.*
import jc.kotlin.template.server.components.*
import kotlinx.html.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

// basic styling for form label/input spacing
val formFieldStyles = setOf("flex", "flex-col", "gap-2")

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

            formInputs()
            radioFormInputs()
            checkboxFormInputs()
            selectFormInputs()
            textareaFormInputs()

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
    val firstRadioGroup = parameters["first_radio_group"].toString()
    val secondRadioGroup = parameters["second_radio_group"].toString()

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
                    dt("font-bold") { +"Favorite Number:" }
                    dd { +firstRadioGroup }
                    dt("font-bold") { +"Favorite Color:" }
                    dd { +secondRadioGroup }
                }
            }
            showValidationErrors(errorMap)
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

    val firstRadioGroup = parameters["first_radio_group"]
    errorMap["first_radio_group"] = if (firstRadioGroup == null) {
        "This field is required"
    } else {
        null
    }
    val secondRadioGroup = parameters["second_radio_group"]
    errorMap["second_radio_group"] = if (secondRadioGroup == null) {
        "This field is required"
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

fun FlowContent.formValidation(input: String, parameters: Parameters) {
    val errorMap = validateForm(parameters)
    // only show validation error for provided input
    showValidationErrors(mapOf(input to errorMap[input]))
}

fun FlowContent.formInputs() {
    // inputs
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
            hxPost("/components/form/validate?input=second_input")
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
}

fun FlowContent.radioFormInputs() {
    // radio buttons
    div {
        classes = setOf("flex", "flex-col", "md:flex-row", "w-full", "justify-between")

        div {
            classes = formFieldStyles
            p {
                required()
                +"Pick your favorite number:"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-3")

                val radioButtonStyles = setOf("flex", "flex-row", "gap-4")
                val radioInputStyles = setOf("cursor-pointer")
                val radioLabelStyles = setOf("cursor-pointer", "block")

                div {
                    classes = radioButtonStyles
                    input {
                        classes = radioInputStyles
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_one"
                        value = "one"
                    }
                    label {
                        classes = radioLabelStyles
                        attributes["for"] = "first_radio_group_one"
                        +"One"
                    }
                }
                div {
                    classes = radioButtonStyles
                    input {
                        classes = radioInputStyles
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_two"
                        value = "two"
                    }
                    label {
                        classes = radioLabelStyles
                        attributes["for"] = "first_radio_group_two"
                        +"Two"
                    }
                }
                div {
                    classes = radioButtonStyles
                    input {
                        classes = radioInputStyles
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_three"
                        value = "three"
                    }
                    label {
                        classes = radioLabelStyles
                        attributes["for"] = "first_radio_group_three"
                        +"Three"
                    }
                }
                p {
                    id = "first_radio_group_error"
                }
            }
        }

        div {
            classes = formFieldStyles
            p {
                required()
                +"Fancier radio - pick your favorite color:"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-1")
                div {
                    classes = setOf("grid", "grid-cols-3", "gap-3")

                    val radioButtonStyles = setOf(
                        "flex",
                        "flex-row",
                        "gap-3",
                        "relative",
                        "border",
                        "w-full",
                        "border-gray-400",
                        "hover:border-gray-500",
                        "rounded-md"
                    )
                    val radioInputStyles =
                        setOf(
                            "peer",
                            "cursor-pointer",
                            "absolute",
                            "opacity-0",
                            "w-full",
                            "h-full",
                            "rounded-md"
                        )
                    val radioLabelStyles =
                        setOf(
                            "cursor-pointer",
                            "block",
                            "p-4",
                            "w-full",
                            "text-center",
                            "rounded-md",
                            "shadow-md",
                            "peer-focus:shadow-lg",
                        )


                    div {
                        classes = radioButtonStyles
                        input {
                            classes = radioInputStyles
                            type = InputType.radio
                            name = "second_radio_group"
                            id = "second_radio_group_yellow"
                            value = "yellow"
                        }
                        label {
                            classes =
                                radioLabelStyles + setOf(
                                    "peer-checked:bg-yellow-100",
                                    "peer-checked:border-yellow-400"
                                )
                            attributes["for"] = "second_radio_group_yellow"
                            +"Yellow"
                        }
                    }
                    div {
                        classes = radioButtonStyles
                        input {
                            classes = radioInputStyles
                            type = InputType.radio
                            name = "second_radio_group"
                            id = "second_radio_group_red"
                            value = "red"
                        }
                        label {
                            classes =
                                radioLabelStyles + setOf(
                                    "peer-checked:bg-red-100",
                                    "peer-checked:border-red-400"
                                )
                            attributes["for"] = "second_radio_group_red"
                            +"Red"
                        }
                    }
                    div {
                        classes = radioButtonStyles
                        input {
                            classes = radioInputStyles
                            type = InputType.radio
                            name = "second_radio_group"
                            id = "second_radio_group_blue"
                            value = "blue"
                        }
                        label {
                            classes =
                                radioLabelStyles + setOf(
                                    "peer-checked:bg-blue-100",
                                    "peer-checked:border-blue-400"
                                )
                            attributes["for"] = "second_radio_group_blue"
                            +"Blue"
                        }
                    }
                }
                p {
                    id = "second_radio_group_error"
                }
            }
        }
    }
}

fun FlowContent.checkboxFormInputs() {

}

fun FlowContent.selectFormInputs() {

}

fun FlowContent.textareaFormInputs() {

}
