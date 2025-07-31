package jc.kotlin.template.server.components

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

val cardBeforeStyles = setOf(
    "absolute",
    "top-0",
    "left-0",
    "right-0",
    "h-px",
    "bg-gradient-to-r",
    "from-transparent",
    "via-gray-200/[0.27]",
    "to-transparent"
)

fun FlowContent.jcCard(
    content: FlowContent.() -> Unit
) {
    div {
        classes = darkCardStyles
        div(cardBeforeStyles.joinToString(" "))
        content()
    }
}