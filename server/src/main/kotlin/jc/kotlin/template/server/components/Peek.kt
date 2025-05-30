package jc.kotlin.template.server.components

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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

enum class PeekSide {
    LEFT, RIGHT
}

/* known issue with multiple peeks on same page not triggering close on click outside
 * this is due to overlapping peek containers on the same z-index, probably solution
 * is to build common peek container layer into root layout and interact with common peek layer
 */
@OptIn(ExperimentalUuidApi::class)
fun FlowContent.peek(
    key: String = Uuid.random().toString().replace("-", ""),
    side: PeekSide = PeekSide.RIGHT,
    peekHeaderText: String = "Peek Header",
    closeBtnText: String = "Close Peek",
    trigger: FlowContent.(String) -> Unit,
    content: FlowContent.() -> Unit
) {
    trigger("${key}peekOpen();")
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
                "bg-white",
                "shadow-xl",
                "overflow-y-auto", // Allow scrolling within the peek if content overflows

                // TODO fix transition
                "transition-transform", // Enable CSS transitions
                "transform",
                "duration-300", // Animation speed
                "ease-in-out", // Animation timing function
                // Initially positioned off-screen to the right/left
//                if (side == PeekSide.RIGHT) "translate-x-full" else "-translate-x-full"
            )

            // --- Peek Content Area ---
            div {
                classes = setOf("p-6", "flex", "flex-col", "gap-4", "h-full") // Padding and layout

                // Header section
                div {
                    classes = setOf("flex", "justify-between", "items-center", "border-b", "pb-3")
                    h3 {
                        classes = setOf("text-lg", "font-semibold")
                        +peekHeaderText
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

                content()

                // Footer section (optional)
                div {
                    classes = setOf(
                        "flex", "items-center", "w-full", "border-t", "pt-3", "mt-auto",
                        if (side == PeekSide.RIGHT) "justify-start" else "justify-end"
                    ) // mt-auto pushes footer to bottom
                    jcButton {
                        onClick = "${key}peekClose();"
                        +closeBtnText
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
                // TODO peek content element is still coupled to peek panel, need to decouple
                """
const ${key}peekContainer = document.getElementById("${key}-peek-container");
const ${key}peekPanel = document.getElementById("${key}-peek-panel");
const ${key}peekContent = document.getElementById("${key}-peek-content");

function ${key}peekOpen() {
    ${key}peekContainer.classList.remove("hidden");
//    ${key}peekPanel.classList.remove("translate-x-full");
}

function ${key}peekClose() {
//    ${key}peekPanel.classList.add("translate-x-full");
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