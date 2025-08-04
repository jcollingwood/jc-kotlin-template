package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

val darkCardStyles = setOf(
    "bg-white/[0.02]",
    "p-8",
    "relative",
    "backdrop-blur-sm",
    "transition-all",
    "duration-300",
    "ease-in-out",
//    "hover:bg-white/[0.04]",
//    "hover:backdrop-blur-md"
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
    val classes: Set<String> = setOf(),
    val accentColor: Color = Color.Peach
)

fun FlowContent.cardAccent(
    props: CardProps = CardProps(),
) {
    val accentClass = when (props.accentColor) {
        Color.Peach -> "via-peach/[0.27]"
        Color.Mint -> "via-mint/[0.27]"
        Color.Purple -> "via-purple/[0.27]"
    }

    div((cardBeforeStyles + setOf(accentClass)).joinToString(" "))
}

fun <T : CommonAttributeGroupFacade> T.card(
    props: CardProps = CardProps(),
): T {
    classes = classes + darkCardStyles + props.classes
    return this
}
