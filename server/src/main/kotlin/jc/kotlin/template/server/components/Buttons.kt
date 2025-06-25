package jc.kotlin.template.server.components

import kotlinx.html.*

val buttonStyles = setOf("border", "py-1", "px-3", "rounded-md", "shadow-md")
val iconButtonStyles = setOf("flex", "flex-row", "items-center", "justify-center", "gap-1")

// default button color is grayscale with white base
data class ButtonColor(
    val text: String = "text-black",
    val hoverText: String = "hover:text-black",
    val color: String = "bg-white",
    val hover: String = "hover:bg-gray-100",
    val active: String = "active:bg-gray-200",
    val border: String = "border-gray-400",
    val activeBorder: String = "active:border-gray-500"
)

enum class IconPosition {
    BEFORE, AFTER
}

fun FlowContent.iconSpan(icon: String, extraClasses: Set<String> = setOf()) {
    span {
        classes = setOf("material-symbols-outlined") + extraClasses
        +icon
    }
}

fun FlowOrInteractiveOrPhrasingContent.jcButton(
    extraClasses: Set<String> = setOf(),
    buttonColor: ButtonColor = ButtonColor(),
    icon: String? = null,
    iconPosition: IconPosition = IconPosition.BEFORE,
    contents: BUTTON.() -> Unit
) {
    val styles = buttonStyles + setOf(
        buttonColor.text,
        buttonColor.hoverText,
        buttonColor.color,
        buttonColor.border,
        buttonColor.hover,
        buttonColor.active,
        buttonColor.activeBorder
    )
    if (icon == null) {
        button {
            classes = styles + extraClasses
            contents()
        }
    } else {
        button {
            classes = styles + iconButtonStyles + extraClasses
            if (iconPosition == IconPosition.BEFORE) iconSpan(icon)
            contents()
            if (iconPosition == IconPosition.AFTER) iconSpan(icon)

        }
    }
}

fun FlowOrInteractiveOrPhrasingContent.jcIconButton(
    icon: String, buttonColor: ButtonColor = ButtonColor(),
    contents: BUTTON.() -> Unit
) {
    val styles = buttonStyles + setOf(
        "!p-2",
        "!rounded-full",
        buttonColor.text,
        buttonColor.hoverText,
        buttonColor.color,
        buttonColor.border,
        buttonColor.hover,
        buttonColor.active,
        buttonColor.activeBorder
    )
    button {
        classes = styles + iconButtonStyles
        contents()
        iconSpan(icon)
    }
}
