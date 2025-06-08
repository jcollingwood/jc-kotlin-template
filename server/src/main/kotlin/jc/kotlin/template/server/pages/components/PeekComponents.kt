package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import kotlinx.html.*

// Main function to render the peek component trigger and structure
fun FlowContent.peekComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Slide-Out Peek"
        }
        p {
            +"Click the button to open a peek that slides in from the right."
        }
        div {
            classes = setOf("flex", "flex-col", "items-start", "gap-4") // items-start to keep button left-aligned

            peek(
                key = "right",
                peekHeaderText = "Right Peek Header",
                trigger = { clickFun ->
                    jcButton {
                        // HTMX attributes to load content
                        hxGet("/components/peek/content") // Example content route
                        hxTarget("#right-peek-content")
                        hxSwap("innerHTML")

                        // JavaScript onClick to trigger the opening animation
                        onClick = clickFun

                        +"Open Right Peek"
                    }
                }
            )
            {
                div {
                    id = "right-peek-content"
                    classes = setOf("flex-grow") // Allows this area to fill remaining space
                    // Initial state / Loading indicator
                    div(classes = "flex justify-center items-center h-full") {
                        jcLoadingDots()
                    }
                }
            }

            peek(
                key = "left",
                side = PeekSide.LEFT,
                peekHeaderText = "Left Peek Header",
                trigger = { clickFun ->
                    jcButton {
                        // HTMX attributes to load content
                        hxGet("/components/peek/content") // Example content route
                        hxTarget("#left-peek-content")
                        hxSwap("innerHTML")

                        // JavaScript onClick to trigger the opening animation
                        onClick = clickFun

                        +"Open Left Peek"
                    }
                }
            )
            {
                div {
                    id = "left-peek-content"
                    classes = setOf("flex-grow") // Allows this area to fill remaining space
                    // Initial state / Loading indicator
                    div(classes = "flex justify-center items-center h-full") {
                        jcLoadingDots()
                    }
                }
            }

            peek(
                key = "static",
                peekHeaderText = "Static Peek Header",
                closeBtnText = "Close-arooni",
                trigger = { clickFun ->
                    jcButton {
                        onClick = clickFun
                        +"Open Static Peek"
                    }
                }
            ) {
                div {
                    p {
                        +"This is static content that does not require HTMX to load."
                    }
                }
            }

        }
    }
}

// Function to generate the dynamic content for the peek
fun FlowContent.peekContent(id: String) {
    div {
        classes = setOf("flex", "flex-col", "gap-3")
        h4 {
            classes = setOf("text-md", "font-medium")
            +"Dynamic Content Loaded"
        }
        p { +"This content was loaded dynamically for ID: $id" }
        p { +"You can put any HTML structure here." }
        ul("list-disc list-inside space-y-1") {
            li { +"Detail 1" }
            li { +"Detail 2 about $id" }
            li { +"Another interesting fact." }
        }
    }
}