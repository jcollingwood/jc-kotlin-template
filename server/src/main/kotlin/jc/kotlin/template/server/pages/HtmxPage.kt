package jc.kotlin.template.server.pages

import jc.kotlin.template.server.components.*
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val htmxSectionClasses = setOf("flex", "flex-col", "gap-6")
val htmxPage = Page("/htmx", "HTMX Page")

fun FlowContent.htmxPage() {
    div {
        classes = setOf("flex", "flex-col", "gap-6", "w-full", "max-w-3xl", "font-xl")

        h1(classes = "header text-xl relative") {
            titleAccent(AccentProps(width = AccentWidth.Title))
            +"HTMX Samples"
        }
        p { +"This section shows off a lot of the HTMX features" }
        div {
            classes = setOf("max-w-3xl", "mt-4", "grid", "grid-cols-1", "sm:grid-cols-3", "gap-4")

            section {
                id = "htmx-section-1"
                classes = htmxSectionClasses

                card()
                cardAccent()

                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
                classes = htmxSectionClasses
                hxGet("/htmx/section/2")
                hxSwap("outerHTML")
                hxTrigger("load delay:1000ms")

                card(props = cardProps)
                cardAccent(props = cardProps)

                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
                    +"HTMX Section 2"
                }
                p {
                    +"This section is loaded via HTMX - triggered new content fetch on initial page load"
                }
            }

            section {
                id = "htmx-section-3"
                classes = htmxSectionClasses
                card()
                cardAccent()
                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
                    btn()
                    btnAccent()

                    +"Reload myself"
                }
            }

            section {
                id = "htmx-section-4"
                classes = htmxSectionClasses
                card()
                cardAccent()
                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
                    btn()
                    btnAccent()

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
                    classes = htmxSectionClasses
                    card()
                    cardAccent()
                    h2 {
                        classes = setOf("text-xl", "relative")
                        titleAccent()
                        +"HTMX Section 5"
                    }
                    p {
                        +"This section replaces itself every 2 seconds"
                    }
                }
            }

            section {
                id = "htmx-section-6"
                classes = htmxSectionClasses + setOf("hover:bg-purple/[0.1]", "transition-colors", "duration-500")
                hxGet("/htmx/section/6")
                hxSwap("outerHTML")
                hxTrigger("mouseenter delay:500ms")
                hxTarget("#htmx-section-6")

                card()
                cardAccent()

                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
                classes = htmxSectionClasses
                card()
                cardAccent()
                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
                    btn()
                    btnAccent()

                    +"Reload with delay"
                }
            }

            section {
                id = "htmx-section-8"
                classes = htmxSectionClasses
                card()
                cardAccent()
                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
                    +"HTMX Section 8"
                }
                p {
                    +"This section shows how HTMX content is able to leverage parameter input"
                }

                span {
                    classes = setOf("grid", "grid-cols-2", "gap-2")
                    Color.entries.map {
                        button {
                            hxGet("/htmx/section/8?color=${it}")
                            hxSwap("outerHTML")
                            hxTrigger("click")
                            hxTarget("#htmx-section-8")
                            val btnProps = BtnProps(
                                classes = setOf("capitalize"),
                                accentColor = it
                            )
                            btn(btnProps)
                            btnAccent(btnProps)

                            +it.name
                        }
                    }
                }
            }

            section {
                id = "htmx-section-9"
                classes = htmxSectionClasses
                card()
                cardAccent()
                h2 {
                    classes = setOf("text-xl", "relative")
                    titleAccent()
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
}

fun FlowContent.genericHtmxSection(sectionNum: Int, color: String? = null) {
    section {
        id = "htmx-section-$sectionNum"
        classes = htmxSectionClasses

        val cardProps = CardProps(
            accentColor = when (color) {
                "Peach" -> Color.Peach
                "Mint" -> Color.Mint
                "Purple" -> Color.Purple
                else -> Color.entries.random()
            }
        )

        card(props = cardProps)
        cardAccent(props = cardProps)

        h2 {
            classes = setOf("text-xl", "relative")
            titleAccent()

            +"HTMX Section $sectionNum"
        }
        p {
            +"HTMX Loaded Section"
        }
        p {
            classes = setOf("text-sm", "text-purple/[0.5]", "italic", "align-self-end")
            +getCurrentTime()
        }
    }
}

fun getCurrentTime(): String = DateTimeFormatter
    .ofPattern("HH:mm:ss")
    .withZone(ZoneOffset.UTC)
    .format(Instant.now())
