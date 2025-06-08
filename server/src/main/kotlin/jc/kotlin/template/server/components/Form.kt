package jc.kotlin.template.server.components

import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.span

val inputStyles = setOf("w-full", "border", "border-gray-400", "py-1", "px-3", "rounded-md", "shadow-md")

fun FlowContent.required() {
    span {
        classes = setOf("text-red-600")
        +"* "
    }
}

val radioStyles = setOf("w-full", "border", "border-gray-400", "py-1", "px-3", "rounded-md", "shadow-md")
