package jc.kotlin.template.server.components.form

import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.classes

fun <T : CommonAttributeGroupFacade> T.checkboxItem(): T {
    classes = setOf(
        "relative",
        "cursor-pointer",
        "flex",
        "items-center",
        "py-3",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "hover:bg-mint/[0.05]",
        "hover:rounded-lg",
        "hover:pl-1"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.checkboxInput(): T {
    classes = setOf(
        "checkbox-input",
        "absolute",
        "opacity-0",
        "cursor-pointer"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.checkboxCustom(): T {
    classes = setOf(
        "checkbox-custom",
        "w-5",
        "h-5",
        "border",
        "border-gray-600",
        "mr-4",
        "relative",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "flex-shrink-0",
        "checked:border-mint"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.checkboxLabel(): T {
    classes = setOf(
        "text-gray-100",
        "font-light",
        "cursor-pointer",
        "flex-grow"
    )
    return this
}
