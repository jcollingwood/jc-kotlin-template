package jc.kotlin.template.app.components

import kotlinx.html.HTML
import kotlinx.html.head
import kotlinx.html.title

fun HTML.appHead(title: String) {
    head {
        title { +title }
        stylesAndFonts()
        matIcons()
        htmx()
    }
}
