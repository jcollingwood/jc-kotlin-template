package jc.kotlin.template.server.components

import jc.kotlin.template.server.utility.Color
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div

data class AccentProps(
    val classes: Set<String> = setOf(),
    val color: Color = Color.Mint,
    val width: AccentWidth = AccentWidth.Third
)

enum class AccentWidth(val classes: Set<String>) {
    Third(setOf("w-1/3")), Title(setOf("w-14")), Half(setOf("w-1/2"));
}

fun FlowContent.titleAccent(
    props: AccentProps = AccentProps()
) {
    val accentBgClass = when (props.color) {
        Color.Peach -> "from-peach"
        Color.Mint -> "from-mint"
        Color.Purple -> "from-purple"
    }
    div {
        classes = setOf(
            "absolute",
            "-bottom-2",
            "left-0",
            "max-w-full",
            "h-px",
            "bg-gradient-to-r",
            accentBgClass,
            "to-transparent"
        ) + props.width.classes + props.classes
    }
}

fun FlowContent.indicatorDot(
    props: AccentProps = AccentProps()
) {
    val accentBgClass = when (props.color) {
        Color.Peach -> "before:bg-peach"
        Color.Mint -> "before:bg-mint"
        Color.Purple -> "before:bg-purple"
    }
    div(
        setOf(
            "before:absolute",
            "before:-left-4",
            "before:top-1/2",
            "before:-translate-y-1/2",
            "before:w-1",
            "before:h-1",
            accentBgClass,
            "before:rounded-full"
        ).joinToString(" ")
    ) {}
}