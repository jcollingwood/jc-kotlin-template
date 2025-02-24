package jc.kotlin.template.app.pages

import jc.kotlin.template.app.components.hxGet
import jc.kotlin.template.app.components.hxIndicator
import jc.kotlin.template.app.components.hxSwap
import jc.kotlin.template.app.components.hxTarget
import jc.kotlin.template.app.components.hxTrigger
import jc.kotlin.template.app.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.span
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val sectionStyles = setOf("h-full", "border", "border-gray-400", "p-4", "rounded-md", "shadow-sm", "space-y-4")

// TODO finalize button style components
val buttonStyles = setOf("border", "border-gray-400", "py-1", "px-3", "rounded-full", "shadow-sm", "hover:bg-gray-100")

val htmxPage = Page("/htmx", "HTMX Page")

fun FlowContent.htmxPage() {
    h1(classes = "text-2xl") { +"HTMX Page" }
    p { +"This section shows off a lot of the HTMX features" }
    div {
        classes = setOf("max-w-3xl", "mt-4", "grid", "grid-cols-1", "sm:grid-cols-3", "gap-4")

        section {
            id = "htmx-section-1"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 1"
            }
            p {
                +"This section is part of the initial page load and not affected by HTMX (yet)"
            }
        }

        section {
            id = "htmx-section-2"
            classes = sectionStyles
            hxGet("/htmx/section/2")
            hxSwap("outerHTML")
            hxTrigger("load delay:1000ms")
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 2"
            }
            p {
                +"This section is loaded via HTMX - triggered new content fetch on initial page load"
            }
        }

        section {
            id = "htmx-section-3"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 3"
            }
            p {
                +"This section will reload when you click the button"
            }
            button {
                hxGet("/htmx/section/3")
                hxSwap("outerHTML")
                hxTrigger("click")
                hxTarget("#htmx-section-3")

                classes = buttonStyles

                +"Reload myself"
            }
        }

        section {
            id = "htmx-section-4"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 4"
            }
            p {
                +"This section will replace section 1 when you click the button"
            }
            button {
                hxGet("/htmx/section/1")
                hxSwap("outerHTML")
                hxTrigger("click")
                hxTarget("#htmx-section-1")

                classes = buttonStyles

                +"Reload Section 1"
            }
        }

        div {
            hxGet("/htmx/section/5")
            hxTarget("#htmx-section-5")
            hxTrigger("every 1800ms")
            hxSwap("outerHTML")
            section {
                id = "htmx-section-5"
                classes = sectionStyles
                h2 {
                    classes = setOf("text-xl")
                    +"HTMX Section 5"
                }
                p {
                    +"This section replaces itself every 2 seconds"
                }
            }
        }

        section {
            id = "htmx-section-6"
            classes = sectionStyles + setOf("hover:bg-gray-100")
            hxGet("/htmx/section/6")
            hxSwap("outerHTML")
            hxTrigger("mouseenter delay:500ms")
            hxTarget("#htmx-section-6")
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 6"
            }
            p {
                +"This section will be replaced on hover"
            }
            p {
                +"Hover Over Me!"
            }
        }

        section {
            id = "htmx-section-7"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 7"
            }
            p {
                +"This section showcases native loading indicator support and a delayed load after retrieval"
            }
            p {
                id = "loading-indicator"
                classes = setOf("htmx-indicator")
                +"Loading... (set 2s delay on server)"
            }
            button {
                hxGet("/htmx/section/7?ms_delay=2000")
                hxSwap("outerHTML swap:1500ms")
                hxTrigger("click")
                hxTarget("#htmx-section-7")
                hxIndicator("#loading-indicator")

                classes = buttonStyles

                +"Reload with delay"
            }
        }

        section {
            id = "htmx-section-8"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 8"
            }
            p {
                +"This section shows how HTMX content is able to leverage parameter input"
            }
            val colors = setOf("red", "green", "blue", "yellow")
            span {
                classes = setOf("grid", "grid-cols-2", "gap-2")
                colors.map {
                    button {
                        hxGet("/htmx/section/8?color=${it}")
                        hxSwap("outerHTML")
                        hxTrigger("click")
                        hxTarget("#htmx-section-8")

                        classes = buttonStyles + setOf("capitalize")

                        +it
                    }
                }
            }
        }

        section {
            id = "htmx-section-9"
            classes = sectionStyles
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 9"
            }
            p {
                +"This section shows how to use input triggers"
            }
            input {
                hxGet("/htmx/section/9")
                hxSwap("outerHTML")
                hxTrigger("keyup delay:2s")
                hxTarget("#htmx-section-9")

                classes =
                    setOf("w-full", "border", "border-gray-400", "py-1", "px-3", "rounded-full", "shadow-sm")

                placeholder = "Typing anything"
            }
        }
    }
}

fun FlowContent.genericHtmxSection(sectionNum: Int, color: String? = null) {
    section {
        id = "htmx-section-$sectionNum"

        val bgs = setOf("bg-red-100", "bg-green-100", "bg-blue-100", "bg-yellow-100")
        val background = if (color == "red") "bg-red-100"
        else if (color == "green") "bg-green-100"
        else if (color == "blue") "bg-blue-100"
        else if (color == "yellow") "bg-yellow-100"
        else bgs.random()

        classes = sectionStyles + setOf(background)
        h2 {
            classes = setOf("text-xl")
            +"HTMX Section $sectionNum"
        }
        p {
            +"HTMX Loaded Section ${getCurrentTime()} "
        }
    }
}

fun getCurrentTime(): String = DateTimeFormatter
    .ofPattern("HH:mm:ss")
    .withZone(ZoneOffset.UTC)
    .format(Instant.now())