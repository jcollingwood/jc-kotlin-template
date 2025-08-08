package jc.kotlin.template.server.components

import kotlinx.html.*

val inputStyles = setOf("w-full", "border", "border-gray-400", "py-1", "px-3", "rounded-md", "shadow-md")

fun FlowContent.required() {
    span {
        classes = setOf("text-peach")
        +"* "
    }
}

fun <T : CommonAttributeGroupFacade> T.formField(): T {
    classes = setOf("relative", "mb-8")
    return this
}

fun FlowContent.inputLabel(
    id: String,
    labelText: String,
    extraClasses: Set<String> = setOf(),
    required: Boolean = false
) {
    label {
        htmlFor = id
        classes = setOf(
            "absolute",
            "left-0",
            "top-4",
            "text-custom-gray",
            "text-base",
            "font-light",
            "transition-all",
            "duration-300",
            "ease-in-out",
            "pointer-events-none",
            "peer-focus:-top-2",
            "peer-focus:text-sm",
            "peer-focus:text-custom-green",
            "peer-[:not(:placeholder-shown)]:-top-2",
            "peer-[:not(:placeholder-shown)]:text-sm",
            "peer-[:not(:placeholder-shown)]:text-custom-green"
        ) + extraClasses
        required()
        +labelText
    }
}

fun <T : CommonAttributeGroupFacade> T.inputField(): T {
    classes = setOf(
        "w-full",
        "max-w-sm",
        "pt-4",
        "pb-4",
        "px-0",
        "bg-transparent",
        "border-0",
        "border-b",
        "border-custom-dark",
        "text-custom-light",
        "text-base",
        "font-light",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "focus:outline-none",
        "focus:border-custom-green",
        "peer"
    )
    return this
}
