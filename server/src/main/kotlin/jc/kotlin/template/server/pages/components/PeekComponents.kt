package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import kotlinx.html.*

// JavaScript for controlling the peek visibility and animation
//const val PEEK_JS = """
//const peekContainer = document.getElementById('peek-container');
//const peekPanel = document.getElementById('peek-panel');
//const peekContent = document.getElementById('peek-content');
//
//function peekOpen() {
//    if (!peekContainer || !peekPanel) return;
//    peekContainer.classList.remove('hidden');
//    // Use requestAnimationFrame to ensure the 'hidden' class removal is processed
//    // before starting the transition by removing the translate class.
//    requestAnimationFrame(() => {
//         peekPanel.classList.remove('translate-x-full');
//    });
//}
//
//function peekClose() {
//    if (!peekContainer || !peekPanel) return;
//    peekPanel.classList.add('translate-x-full');
//
//    // Wait for the transition to finish before hiding the container and clearing content
//    peekPanel.addEventListener('transitionend', () => {
//        peekContainer.classList.add('hidden');
//        if (peekContent) {
//            peekContent.innerHTML = ''; // Clear content after closing
//        }
//    }, { once: true }); // Important: Use { once: true } to auto-remove the listener
//}
//
//// Close peek if clicking on the background overlay
///*if (peekContainer) {
//    peekContainer.addEventListener('click', function(event) {
//        if (event.target === peekContainer) {
//            peekClose();
//        }
//    });
//}*/
//
//"""

const val PEEK_JS = """
const peekContainer = document.getElementById("peek-container");
const peekContent = document.getElementById("peek-content");

function peekOpen() {
    peekContainer.classList.remove("hidden");
}

function peekClose() {
    peekContainer.classList.add("hidden");
    peekContent.innerHTML = "";
}

window.onclick = function(event) {
    if(event.target == peekContainer) {
        peekClose();
    }
}
"""

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

            // --- Trigger Button ---
            jcButton {
                // HTMX attributes to load content
                hxGet("/components/peek/content") // Example content route
                hxTarget("#peek-content")
                hxSwap("innerHTML")
                // Indicator within the button while loading
//                span("htmx-indicator") { jcLoadingDots() }

                // JavaScript onClick to trigger the opening animation
                onClick = "peekOpen();"

                +"Open Peek"
            }

            // --- Peek Structure (Hidden by default) ---
            div {
                id = "peek-container"

                // Styling for the overlay background
                classes = setOf(
                    "hidden", // Initially hidden
                    "fixed",
                    "inset-0",
                    "z-40", // High z-index, but potentially lower than a peek if needed
                    "bg-black",
                    "bg-opacity-50",
                    "overflow-hidden" // Prevent scrollbars on the overlay
                )

                // The actual peek panel that slides
                div {
                    id = "peek-panel"
                    // Styling and positioning for the panel
                    classes = setOf(
                        "absolute",
                        "top-0",
                        "left-0",
                        "h-full",
                        "w-full", // Full width on small screens
                        "sm:w-1/3", // Takes 1/3 of the width on larger screens
                        "lg:w-1/4", // Takes 1/4 on even larger screens
                        "bg-white",
                        "shadow-xl",
                        "overflow-y-auto", // Allow scrolling within the peek if content overflows
                        "transform",
                        "transition-transform", // Enable CSS transitions
                        "duration-300", // Animation speed
                        "ease-in-out", // Animation timing function
                        "translate-x-full" // Initially positioned off-screen to the right
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
                                onClick = "peekClose();"
                                span {
                                    classes = setOf("material-symbols-outlined")
                                    +"close"
                                }
                            }
                        }

                        // Area where HTMX will load content
                        div {
                            id = "peek-content"
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
                                onClick = "peekClose();"
                                +"Close Peek"
                            }
                        }
                    }
                }
            }
        }
    }

    // Inject the JavaScript
    script {
        unsafe { raw(PEEK_JS) }
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
        // Example of another button inside the loaded content
        jcButton {
            onClick = "alert('Action clicked inside peek!');"

            +"Action inside Peek"
        }
    }
}