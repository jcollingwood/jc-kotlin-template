package jc.kotlin.template.server.components

import kotlinx.html.BUTTON
import kotlinx.html.FlowOrInteractiveOrPhrasingContent
import kotlinx.html.button
import kotlinx.html.classes

val buttonStyles = setOf("border", "border-gray-400", "py-1", "px-3", "rounded-md", "shadow-sm", "hover:bg-gray-100")

fun FlowOrInteractiveOrPhrasingContent.jcButton(
    extraClasses: Set<String> = setOf(),
    // TODO enumerate styling options?
    contents: BUTTON.() -> Unit
) {
    button {
        classes = buttonStyles + extraClasses
        contents()
    }
}
