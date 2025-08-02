package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

val cardStyles = setOf(
    "w-full",
    "p-4",
    "border",
    "border-gray-200",
    "rounded-md",
    "shadow-md",
    "items-center"
)

val darkCardStyles = setOf(
    "bg-white/[0.02]",
    "p-8",
    "mb-8",
    "relative",
    "backdrop-blur-sm",
    "transition-all",
    "duration-300",
    "ease-in-out",
    "hover:bg-white/[0.04]",
    "hover:backdrop-blur-md"
)

//    .card-accent::after {
//    content: '';
//    position: absolute;
//    left: 0;
//    top: 2rem;
//    bottom: 2rem;
//    width: 2px;
//    background: linear-gradient(to bottom, transparent, #ffd3a5, transparent);
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
    val accentColor: Color = Color.Peach
)

fun FlowContent.jcCard(
    cardProps: CardProps = CardProps(),
    content: FlowContent.() -> Unit
) {
    val accentClass = when (cardProps.accentColor) {
        Color.Peach -> "via-peach/[0.27]"
        Color.Mint -> "via-mint/[0.27]"
        Color.Purple -> "via-purple/[0.27]"
    }

    div {
        classes = darkCardStyles
        div((cardBeforeStyles + setOf(accentClass)).joinToString(" "))
        content()
    }
}