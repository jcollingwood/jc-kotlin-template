package jc.kotlin.template.server.components

import jc.kotlin.template.server.pages.components.jcLoadingDots
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.onClick
import kotlinx.html.script
import kotlinx.html.span
import kotlinx.html.unsafe

enum class PeekSide {
    LEFT, RIGHT
}

fun FlowContent.peek(key: String, side: PeekSide = PeekSide.RIGHT) {
    // --- Trigger Button ---
    jcButton {
        // HTMX attributes to load content
        hxGet("/components/peek/content") // Example content route
        hxTarget("#${key}-peek-content")
        hxSwap("innerHTML")
        // Indicator within the button while loading
//                span("htmx-indicator") { jcLoadingDots() }

        // JavaScript onClick to trigger the opening animation
        onClick = "${key}peekOpen();"

        +"Open $key Peek"
    }

    div {
        id = "${key}-peek-container"

        // Styling for the overlay background
        classes = setOf(
            "hidden", // Initially hidden
            "fixed",
            "inset-0",
            "z-40", // High z-index, but potentially lower than a peek if needed
            "bg-black",
            "transition-opacity",
            "bg-opacity-50",
            "overflow-hidden" // Prevent scrollbars on the overlay
        )

        // The actual peek panel that slides
        div {
            id = "${key}-peek-panel"

            // Styling and positioning for the panel
            classes = setOf(
                "absolute",
                "top-0",
                if (side == PeekSide.RIGHT) "right-0" else "",
                "m-3",
                "rounded-lg",
                "h-[calc(100vh-3rem)]", // Full height minus some margin
                "w-full", // Full width on small screens
                "sm:w-1/2", // Takes 1/3 of the width on larger screens
                "md:w-1/3", // Takes 1/4 on even larger screens
                "bg-white",
                "shadow-xl",
                "overflow-y-auto", // Allow scrolling within the peek if content overflows

                // TODO fix transition
                "transition-transform", // Enable CSS transitions
                "transform",
                "duration-300", // Animation speed
                "ease-in-out", // Animation timing function
                // Initially positioned off-screen to the right/left
                if (side == PeekSide.RIGHT) "translate-x-full" else "-translate-x-full"
            )

            // --- Peek Content Area ---
            div {
                classes = setOf("p-6", "flex", "flex-col", "gap-4", "h-full") // Padding and layout

                // Header section
                div {
                    classes = setOf("flex", "justify-between", "items-center", "border-b", "pb-3")
                    h3 {
                        classes = setOf("text-lg", "font-semibold")
                        +"Peek Header"
                    }
                    // Close Button
                    button {
                        classes = setOf(
                            "text-gray-500",
                            "hover:text-gray-700",
                            "focus:outline-none"
                        )
                        onClick = "${key}peekClose();"
                        span {
                            classes = setOf("material-symbols-outlined")
                            +"close"
                        }
                    }
                }

                // Area where HTMX will load content
                div {
                    id = "${key}-peek-content"
                    classes = setOf("flex-grow") // Allows this area to fill remaining space
                    // Initial state / Loading indicator
                    div(classes = "flex justify-center items-center h-full") {
                        jcLoadingDots()
                    }
                }

                // Footer section (optional)
                div {
                    classes = setOf("border-t", "pt-3", "mt-auto") // mt-auto pushes footer to bottom
                    jcButton {
                        onClick = "${key}peekClose();"
                        +"Close Peek"
                    }
                }
            }
        }
    }

    // Inject the JavaScript
    script {
        unsafe {
            raw(
// JavaScript for controlling the peek visibility and animation
                """
const ${key}peekContainer = document.getElementById("${key}-peek-container");
const ${key}peekPanel = document.getElementById("${key}-peek-panel");
const ${key}peekContent = document.getElementById("${key}-peek-content");

function ${key}peekOpen() {
    ${key}peekContainer.classList.remove("hidden");
    ${key}peekPanel.classList.remove("translate-x-full");
}

function ${key}peekClose() {
    ${key}peekPanel.classList.add("translate-x-full");
    ${key}peekContainer.classList.add("hidden");
    ${key}peekContent.innerHTML = "";
}

window.onclick = function(event) {
    if(event.target == ${key}peekContainer) {
        ${key}peekClose();
    }
}
"""
            )
        }
    }
}