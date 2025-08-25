package jc.kotlin.template.server.components.form

import jc.kotlin.template.server.components.iconSpan
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.classes

// TODO add color options

fun <T : CommonAttributeGroupFacade> T.selectWrapper(): T {
    classes = setOf(
        "select-wrapper",
        "relative",
        "my-8"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelect(): T {
    classes = setOf(
        "form-select",
        "w-full",
        "py-4",
        "px-2",
        "bg-transparent",
        "border-b",
        "border-gray-600",
        "text-gray-100",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "cursor-pointer",
        "appearance-none",
        // focus styles
        "focus:outline-none",
        "focus:border-mint",
        // valid styles
        "valid:focus:border-mint",
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelectOption(): T {
    classes = setOf(
        "bg-dark-800",
        "text-gray-100",
        "p-2",
        "border-none",
        // hover styles
        "hover:bg-dark-700"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelectLabel(): T {
    classes = setOf(
        "form-label",
        "absolute",
        "left-0",
        "text-gray-500",
        "text-base",
        "font-light",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "pointer-events-none"
    )
    return this
}

fun FlowContent.selectArrow() {
    iconSpan(
        icon = "keyboard_arrow_down",
        extraClasses = setOf(
            "select-arrow",
            "absolute",
            "right-0",
            "top-1/2",
            "transform",
            "-translate-y-1/2",
            "pointer-events-none",
            "transition-all",
            "duration-300",
            "ease-in-out"
        )
    )
}

