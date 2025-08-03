package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
import kotlinx.html.*

//val cardStyles = setOf(
//    "w-full",
//    "p-4",
//    "border",
//    "border-gray-200",
//    "rounded-md",
//    "shadow-md",
//    "items-center"
//)

val darkCardStyles = setOf(
    "bg-white/[0.02]",
    "p-8",
    "relative",
    "backdrop-blur-sm",
    "transition-all",
    "duration-300",
    "ease-in-out",
    "hover:bg-white/[0.04]",
    "hover:backdrop-blur-md"
)

val cardBeforeStyles = setOf(
    "absolute",
    "top-2",
    "left-0",
    "bottom-2",
    "w-[2px]",
    "bg-gradient-to-b",
    "from-transparent",
    "to-transparent"
)

data class CardProps(
    val id: String? = null,
    val classes: Set<String> = setOf(),
    val accentColor: Color = Color.Peach
)

fun FlowContent.jcCard(
    props: CardProps = CardProps(),
    content: FlowContent.() -> Unit
) {
    val accentClass = when (props.accentColor) {
        Color.Peach -> "via-peach/[0.27]"
        Color.Mint -> "via-mint/[0.27]"
        Color.Purple -> "via-purple/[0.27]"
    }

    div {
        props.id?.let { id = it }
        classes = darkCardStyles + props.classes
        div((cardBeforeStyles + setOf(accentClass)).joinToString(" "))
        content()
    }
}

fun DIV.asCard(
    props: CardProps = CardProps(),
): DIV {
    props.id?.let { id = it }
    classes = darkCardStyles + props.classes

    div(
        (cardBeforeStyles + setOf(
            when (props.accentColor) {
                Color.Peach -> "via-peach/[0.27]"
                Color.Mint -> "via-mint/[0.27]"
                Color.Purple -> "via-purple/[0.27]"
            }
        )).joinToString(" ")
    )

    return this
}