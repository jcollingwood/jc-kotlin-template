package jc.kotlin.template.server.components

import kotlinx.html.HTML
import kotlinx.html.head
import kotlinx.html.meta
import kotlinx.html.title

fun HTML.appHead(title: String) {
    head {
        title { +title }
        meta { name = "viewport"; content = "width=device-width, initial-scale=1" }
        stylesAndFonts()
        matIcons()
        htmx()
    }
}
