package jc.kotlin.template.app.components

import jc.kotlin.template.app.htmx
import jc.kotlin.template.app.matIcons
import jc.kotlin.template.app.stylesAndFonts
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
