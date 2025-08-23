package jc.kotlin.template.server.pages.components

import io.ktor.http.*
import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.components.form.*
import jc.kotlin.template.server.components.form.radioInput
import kotlinx.html.*

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

            span {
                classes = setOf("mt-4")
                button {
                    classes = setOf("pr-10")
                    type = ButtonType.submit

                    btn()
                    btnAccent()

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
    val firstCheckboxOne = parameters["first_checkbox_one"].toString()
    val firstCheckboxTwo = parameters["first_checkbox_two"].toString()
    val firstCheckboxThree = parameters["first_checkbox_three"].toString()
    val secondCheckboxYellow = parameters["second_checkbox_yellow"].toString()
    val secondCheckboxBlue = parameters["second_checkbox_blue"].toString()
    val secondCheckboxRed = parameters["second_checkbox_red"].toString()
    val firstSelect = parameters["first_select"].toString()
    val firstTextarea = parameters["first_textarea"].toString()

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

                    dt("font-bold") { +"Likes One:" }
                    dd { +(if (firstCheckboxOne == "one") "yes" else "no") }
                    dt("font-bold") { +"Likes Two:" }
                    dd { +(if (firstCheckboxTwo == "two") "yes" else "no") }
                    dt("font-bold") { +"Likes Three:" }
                    dd { +(if (firstCheckboxThree == "three") "yes" else "no") }

                    dt("font-bold") { +"Likes Yellow:" }
                    dd { +(if (secondCheckboxYellow == "yellow") "yes" else "no") }
                    dt("font-bold") { +"Likes Blue:" }
                    dd { +(if (secondCheckboxBlue == "blue") "yes" else "no") }
                    dt("font-bold") { +"Likes Red:" }
                    dd { +(if (secondCheckboxRed == "red") "yes" else "no") }

                    dt("font-bold") { +"First Select" }
                    dd { +firstSelect }
                    dt("font-bold") { +"First Textarea" }
                    dd { +firstTextarea }
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

    val firstSelect = parameters["first_select"]
    errorMap["first_select"] = if (firstSelect == null) {
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
        formField()

        input {
            inputField()

            type = InputType.text
            id = "first_input"
            name = "first_input"

            placeholder = " "
        }
        inputLabel(
            id = "first_input",
            labelText = "First Input",
        )
    }

    span {
        formField()

        input {
            hxPost("/components/form/validate?input=second_input")
            hxTrigger("change")
            hxSwap()
            hxSync()

            inputField()

            type = InputType.text
            id = "second_input"
            name = "second_input"

            placeholder = " "
        }
        inputLabel(
            id = "second_input",
            labelText = "Second Input w/ Validations",
            required = true
        )
        p {
            id = "second_input_error"
        }
    }
}

fun FlowContent.radioFormInputs() {
    // radio buttons
    div {
        classes = setOf("flex", "flex-col", "w-full", "justify-between")

        div {
            classes = formFieldStyles
            p {
                required()
                +"Pick your favorite number:"
            }
            div {
                classes = setOf("flex", "flex-col", "mb-4")

                label {
                    radioItem()
                    input {
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_one"
                        value = "one"
                        radioInput()
                    }
                    span { radioCustom() }
                    span {
                        attributes["for"] = "first_radio_group_one"
                        radioLabel()
                        +"One"
                    }
                }
                label {
                    radioItem()
                    input {
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_two"
                        value = "two"
                        radioInput()
                    }
                    span { radioCustom() }
                    span {
                        attributes["for"] = "first_radio_group_two"
                        radioLabel()
                        +"Two"
                    }
                }
                label {
                    radioItem()
                    input {
                        type = InputType.radio
                        name = "first_radio_group"
                        id = "first_radio_group_three"
                        value = "three"
                        radioInput()
                    }
                    span { radioCustom() }
                    span {
                        attributes["for"] = "first_radio_group_three"
                        radioLabel()
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
                        "w-full",
                        "border",
                        "border-gray-700",
                        "hover:border-gray-600",
                        "rounded-sm",
                        "hover:translate-y-[-1px]"
                    )
                    val radioInputStyles =
                        setOf(
                            "peer",
                            "cursor-pointer",
                            "absolute",
                            "opacity-0",
                            "w-full",
                            "h-full",
                            "rounded-sm"
                        )
                    val radioLabelStyles =
                        setOf(
                            "cursor-pointer",
                            "block",
                            "p-4",
                            "w-full",
                            "text-center",
                            "rounded-sm",
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
                                    "peer-checked:bg-peach/[0.1]",
                                    "peer-checked:border-peach"
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
                                    "peer-checked:bg-purple/[0.1]",
                                    "peer-checked:border-purple"
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
                                    "peer-checked:bg-mint/[0.1]",
                                    "peer-checked:border-mint"
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
    span {
        classes = formFieldStyles
        div {
            label {
                +"More than one favorite number?"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-1")
                label {
                    checkboxItem()
                    input {
                        type = InputType.checkBox
                        name = "first_checkbox_one"
                        id = "first_checkbox_one"
                        value = "one"
                        checkboxInput()
                    }
                    span { checkboxCustom() }
                    span {
                        classes = setOf("cursor-pointer", "block")
                        attributes["for"] = "first_checkbox_one"
                        checkboxLabel()
                        +"One"
                    }
                }
                label {
                    checkboxItem()
                    input {
                        type = InputType.checkBox
                        name = "first_checkbox_two"
                        id = "first_checkbox_two"
                        value = "two"
                        checkboxInput()
                    }
                    span { checkboxCustom() }
                    span {
                        attributes["for"] = "first_checkbox_two"
                        checkboxLabel()
                        +"Two"
                    }
                }
                label {
                    checkboxItem()
                    input {
                        type = InputType.checkBox
                        name = "first_checkbox_three"
                        id = "first_checkbox_three"
                        value = "three"
                        checkboxInput()
                    }
                    span { checkboxCustom() }
                    span {
                        attributes["for"] = "first_checkbox_three"
                        checkboxLabel()
                        +"Three"
                    }
                }
            }
        }

        div {
            classes = formFieldStyles
            label {
                +"More than one favorite color?"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-1")
                div {
                    classes = setOf("grid", "grid-cols-3", "gap-3")

                    val checkboxButtonStyles = setOf(
                        "flex",
                        "flex-row",
                        "gap-3",
                        "relative",
                        "border",
                        "w-full",
                        "border-gray-700",
                        "hover:border-gray-600",
                        "rounded-sm",
                        "hover:translate-y-[-1px]"
                    )
                    val checkboxInputStyles =
                        setOf(
                            "peer",
                            "cursor-pointer",
                            "absolute",
                            "opacity-0",
                            "w-full",
                            "h-full",
                            "rounded-sm"
                        )
                    val checkboxLabelStyles =
                        setOf(
                            "cursor-pointer",
                            "block",
                            "p-4",
                            "w-full",
                            "text-center",
                            "rounded-sm",
                            "shadow-md",
                            "peer-focus:shadow-lg",
                        )


                    div {
                        classes = checkboxButtonStyles
                        input {
                            classes = checkboxInputStyles
                            type = InputType.checkBox
                            name = "second_checkbox_yellow"
                            id = "second_checkbox_yellow"
                            value = "yellow"
                        }
                        label {
                            classes =
                                checkboxLabelStyles + setOf(
                                    "peer-checked:bg-peach/[0.1]",
                                    "peer-checked:border-peach"
                                )
                            attributes["for"] = "second_checkbox_yellow"
                            +"Yellow"
                        }
                    }
                    div {
                        classes = checkboxButtonStyles
                        input {
                            classes = checkboxInputStyles
                            type = InputType.checkBox
                            name = "second_checkbox_red"
                            id = "second_checkbox_red"
                            value = "red"
                        }
                        label {
                            classes =
                                checkboxLabelStyles + setOf(
                                    "peer-checked:bg-purple/[0.1]",
                                    "peer-checked:border-purple"
                                )
                            attributes["for"] = "second_checkbox_red"
                            +"Red"
                        }
                    }
                    div {
                        classes = checkboxButtonStyles
                        input {
                            classes = checkboxInputStyles
                            type = InputType.checkBox
                            name = "second_checkbox_blue"
                            id = "second_checkbox_blue"
                            value = "blue"
                        }
                        label {
                            classes =
                                checkboxLabelStyles + setOf(
                                    "peer-checked:bg-mint/[0.1]",
                                    "peer-checked:border-mint"
                                )
                            attributes["for"] = "second_checkbox_blue"
                            +"Blue"
                        }
                    }
                }
            }
        }
    }
}

fun FlowContent.selectFormInputs() {
    span {
        span {
            selectWrapper()

            select {
                name = "first_select"
                formSelect()

                option {
                    value = ""
                    disabled = true
                    attributes["selected"] = "true"

                    formSelectOption()
                }
                option {
                    value = "one"
                    formSelectOption()
                    +"One"
                }
                option {
                    value = "two"
                    formSelectOption()
                    +"Two"
                }
                option {
                    value = "three"
                    formSelectOption()
                    +"Three"
                }
            }
            label {
                formSelectLabel()
                +"Select"
            }
            selectArrow()
        }

        p {
            id = "first_select_error"
        }
    }
}
