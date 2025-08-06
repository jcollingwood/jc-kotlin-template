package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
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

// TODO remove - old buttons

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

// new buttons
// TODO icon buttons

enum class BtnType {
    Primary, Secondary, Tertiary
}

data class BtnProps(
    val classes: Set<String> = setOf(),
    val accentColor: Color = Color.Mint,
    val type: BtnType = BtnType.Primary
)

val primaryBtnAccentStyles = setOf(
    "absolute",
    "top-0",
    "left-2",
    "right-2",
    "h-[2px]",
    "bg-gradient-to-r",
    "from-transparent",
    "to-transparent"
)
val secondaryBtnAccentStyles = setOf(
    "transition-all",
    "duration-300",
    "group-hover:w-full",
)

fun FlowContent.btnAccent(
    props: BtnProps = BtnProps(),
) {
    val accentClass = when (props.accentColor) {
        Color.Peach -> "via-peach/[0.27]"
        Color.Mint -> "via-mint/[0.27]"
        Color.Purple -> "via-purple/[0.27]"
    }

    when (props.type) {
        BtnType.Primary -> {
            div((primaryBtnAccentStyles + setOf(accentClass)).joinToString(" "))
        }

        BtnType.Secondary -> {
            val secondaryAccentClass = when (props.accentColor) {
                Color.Peach -> setOf("group-hover:to-peach")
                Color.Mint -> setOf("group-hover:to-mint")
                Color.Purple -> setOf("group-hover:to-purple")
            }
            titleAccent(
                props = AccentProps(
                    color = props.accentColor,
                    classes = secondaryBtnAccentStyles + secondaryAccentClass
                )
            )
        }

        BtnType.Tertiary -> {
            // Tertiary buttons do not have an accent
            return
        }
    }
}

// TODO button click animations
val primaryBtnClasses = setOf(
    "px-6",
    "py-3",
    "bg-white/[0.02]",
    "border-none",
    "text-gray-300",
    "font-light",
    "cursor-pointer",
    "relative",
    "transition-all",
    "duration-300",
    "ease-in-out",
    "text-base",
    "no-underline",
    "inline-block",
    "hover:bg-white/[0.04]",
    "hover:text-white",
    "hover:translate-y-[-1px]"
)

val secondaryBtnClasses = setOf(
    "text-zinc-400",
    "no-underline",
    "font-light",
    "transition-all",
    "duration-300",
    "relative",
    "hover:text-white",
    "group"
)

fun <T : CommonAttributeGroupFacade> T.btn(
    props: BtnProps = BtnProps(),
): T {
    classes = classes +
            when (props.type) {
                BtnType.Primary -> primaryBtnClasses
                BtnType.Secondary -> secondaryBtnClasses
                BtnType.Tertiary -> primaryBtnClasses
            } + props.classes
    return this
}
