package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

data class AccentProps(
    val classes: Set<String> = setOf(),
    val accentColor: Color = Color.Mint
)

fun FlowContent.titleAccent(
    accentProps: AccentProps = AccentProps()
) {
    val accentBgClass = when (accentProps.accentColor) {
        Color.Peach -> "from-peach"
        Color.Mint -> "from-mint"
        Color.Purple -> "from-purple"
    }
    div {
        classes = setOf(
            "absolute",
            "-bottom-2",
            "left-0",
            "w-10",
            "h-px",
            "bg-gradient-to-r",
            accentBgClass,
            "to-transparent"
        ) + accentProps.classes
    }
}