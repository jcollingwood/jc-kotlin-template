package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.*
import kotlinx.html.*

// JavaScript for controlling the peek visibility and animation
const val PEEK_JS = """
const peekContainer = document.getElementById("peek-container");
const peekPanel = document.getElementById("peek-panel");
const peekContent = document.getElementById("peek-content");

function peekOpen() {
    peekContainer.classList.remove("hidden");
    peekPanel.classList.remove("translate-x-full");
}

function peekClose() {
    peekPanel.classList.add("translate-x-full");
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

            peek("right")
            peek("left", PeekSide.LEFT)
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