package jc.kotlin.template.server.components

import kotlinx.html.FlowOrMetaDataOrPhrasingContent
import kotlinx.html.link
import kotlinx.html.script

fun FlowOrMetaDataOrPhrasingContent.stylesAndFonts() {
    // favicon
    link(rel = "icon", href = "/static/favicon.ico", type = "image/x-icon")

    // tailwind css stylesheet
    link(rel = "stylesheet", href = "/static/styles.css", type = "text/css")
    link(rel = "stylesheet", href = "/static/jc_styles.css", type = "text/css")

    // google fonts + Inter font
    link(rel = "preconnect", href = "https://fonts.googleapis.com")
    link(rel = "preconnect", href = "https://fonts.gstatic.com")
    link(
        href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500&display=swap",
        rel = "stylesheet"
    )

    // custom stylesheet
    link(rel = "stylesheet", href = "/static/jc_dark.css", type = "text/css")
}

fun FlowOrMetaDataOrPhrasingContent.htmx() {
    // htmx dep
    script { src = "https://unpkg.com/htmx.org@2.0.2" }
}

fun FlowOrMetaDataOrPhrasingContent.matIcons() {
    link(
        rel = "stylesheet",
        href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,400,0,0"
    )
}