package jc.kotlin.template.server.pages

import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val sectionStyles = setOf("h-full", "border", "border-gray-400", "p-4", "rounded-md", "shadow-sm", "space-y-4")

val htmxPage = Page("/htmx", "HTMX Page")

fun FlowContent.htmxPage() {
    h1(classes = "text-2xl") { +"HTMX Page" }
    p { +"This section shows off a lot of the HTMX features" }
    div {
        classes = setOf("max-w-3xl", "mt-4", "grid", "grid-cols-1", "sm:grid-cols-3", "gap-4")

        section {
            id = "htmx-section-1"

            asCard()
            cardAccent()

            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 1"
            }
            p {
                +"This section is part of the initial page load and not affected by HTMX (yet)"
            }
        }

        section {
            val cardProps = CardProps(
                accentColor = Color.Mint
            )

            id = "htmx-section-2"
            hxGet("/htmx/section/2")
            hxSwap("outerHTML")
            hxTrigger("load delay:1000ms")

            asCard(props = cardProps)
            cardAccent(props = cardProps)

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
            asCard()
            cardAccent()
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 3"
            }
            p {
                +"This section will reload when you click the button"
            }
            jcButton {
                hxGet("/htmx/section/3")
                hxSwap("outerHTML")
                hxTrigger("click")
                hxTarget("#htmx-section-3")

                +"Reload myself"
            }
        }

        section {
            id = "htmx-section-4"
            asCard()
            cardAccent()
            h2 {
                classes = setOf("text-xl")
                +"HTMX Section 4"
            }
            p {
                +"This section will replace section 1 when you click the button"
            }
            jcButton {
                hxGet("/htmx/section/1")
                hxSwap("outerHTML")
                hxTrigger("click")
                hxTarget("#htmx-section-1")

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
                asCard()
                cardAccent()
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
            classes = setOf("hover:bg-gray-100")
            hxGet("/htmx/section/6")
            hxSwap("outerHTML")
            hxTrigger("mouseenter delay:500ms")
            hxTarget("#htmx-section-6")

            asCard()
            cardAccent()

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
            asCard()
            cardAccent()
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
            jcButton {
                hxGet("/htmx/section/7?ms_delay=2000")
                hxSwap("outerHTML swap:1500ms")
                hxTrigger("click")
                hxTarget("#htmx-section-7")
                hxIndicator("#loading-indicator")

                +"Reload with delay"
            }
        }

        section {
            id = "htmx-section-8"
            asCard()
            cardAccent()
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
                    jcButton(extraClasses = setOf("capitalize")) {
                        hxGet("/htmx/section/8?color=${it}")
                        hxSwap("outerHTML")
                        hxTrigger("click")
                        hxTarget("#htmx-section-8")

                        +it
                    }
                }
            }
        }

        section {
            id = "htmx-section-9"
            asCard()
            cardAccent()
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

                classes = inputStyles

                placeholder = "Typing anything"
            }
        }
    }
}

fun FlowContent.genericHtmxSection(sectionNum: Int, color: String? = null) {
    section {
        id = "htmx-section-$sectionNum"

        val cardProps = CardProps(
            accentColor = when (color) {
                "red" -> Color.Peach
                "green" -> Color.Mint
                "blue" -> Color.Purple
                "yellow" -> Color.Peach
                else -> Color.Peach // default color
            }
        )

        asCard(props = cardProps)
        cardAccent(props = cardProps)

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
