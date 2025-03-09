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

fun FlowContent.jcCard(
    content: FlowContent.() -> Unit
) {
    div {
        classes = cardStyles
        content()
    }
}