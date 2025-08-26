package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import kotlinx.html.*

const val MODAL_JS = """
const modalContainer = document.getElementById("modal-container");
const modalContent = document.getElementById("modal-content");

function modalOpen() {
    modalContainer.classList.remove("hidden");
}

function modalClose() {
    modalContainer.classList.add("hidden");
    modalContent.innerHTML = "";
}

window.onclick = function(event) {
    if(event.target == modalContainer) {
        modalClose();
    }
}
"""

fun FlowContent.modalComponents() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        div {
            classes = setOf("flex", "flex-col", "gap-4")
            h2 {
                classes = setOf("text-xl")
                +"Modal"
            }
            p {
                +"Sample modal to showcase HTMX modal submission and it's bells and whistles"
            }
            div {
                classes = setOf("flex", "flex-col", "gap-4")

                jcButton {
                    id = "modal-open-button"
                    hxGet("/components/modal/content/1")
                    hxTarget("#modal-content")
                    hxSwap("innerHTML")

                    onClick = "modalOpen();"

                    +"Open Modal 1"
                }
                jcButton {
                    id = "modal-open-button"
                    hxGet("/components/modal/content/2")
                    hxTarget("#modal-content")
                    hxSwap("innerHTML")

                    onClick = "modalOpen();"

                    +"Open Modal 2"
                }
                jcButton {
                    id = "modal-open-button"
                    hxGet("/components/modal/content/3")
                    hxTarget("#modal-content")
                    hxSwap("innerHTML")

                    onClick = "modalOpen();"

                    +"Open Modal 3"
                }
            }
            /*
             * modal container is hidden by default. pattern here is for triggering button to show modal via javascript
             * as well as trigger content to populate via htmx
             */
            div {
                id = "modal-container"

                classes = setOf(
                    "hidden",
                    "fixed",
                    "inset-0",
                    "z-10",
                    "overflow-y-auto",
                    "bg-black",
                    "bg-opacity-50"
                )
                div {
                    classes = setOf(
                        "flex",
                        "min-h-full",
                        "items-center",
                        "justify-center",
                        "p-4",
                        "text-center"
                    )
                    div {
                        classes = setOf(
                            "relative",
                            "transform",
                            "overflow-hidden",
                            "rounded-lg",
                            "bg-white",
                            "text-left",
                            "shadow-xl",
                            "transition-all",
                            "sm:my-8",
                            "sm:w-full",
                            "sm:max-w-lg"
                        )
                        section {
                            card()
                            cardAccent()
                            div {
                                classes = setOf("flex", "flex-col", "gap-6")
                                h1 {
                                    +"Modal Header"
                                }
                                div {
                                    id = "modal-content"
                                    jcLoadingDots()
                                }
                                div {
                                    classes = setOf("htmx-indicator")
                                    jcLoadingDots()
                                }
                                jcButton {
                                    id = "modal-close-button"

                                    onClick = "modalClose();"

                                    +"Close"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    script {
        // just dropping in raw javascript...
        // probably a better way to do this but this is simple enough for now
        unsafe { raw(MODAL_JS) }
    }
}

fun FlowContent.modalContent(id: String) {
    // simulating longer submission delay to show off loading
    Thread.sleep(800)

    section {
        p {
            +"This is the content for modal $id"
        }
    }
}

fun FlowContent.jcLoadingDots() {
    div {
        classes = setOf("flex", "flex-row", "gap-2")
        listOf("delay-75", "delay-150", "delay-300").map { delay ->
            span {
                classes = setOf("w-2", "h-2", "bg-gray-400", "rounded-full", "animate-bounce", delay)
            }
        }
    }
}

