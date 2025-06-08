package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.jcCard
import kotlinx.html.*

fun FlowContent.cardComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")
        h2 {
            classes = setOf("text-xl")
            +"Card"
        }
        p {
            +"""
            Also boring... 
            basic card available as component or basic styles constant is also a viable solution.
            Cards by default will take up the entire width given by their parent.
            """
        }
        jcCard {
            +"This is a standard card... cardception..."
        }
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-2", "gap-4")
            jcCard { +"These" }
            jcCard { +"Cards" }
            jcCard { +"Are" }
            jcCard { +"In" }
            jcCard { +"A" }
            jcCard { +"Grid" }
        }
    }
}