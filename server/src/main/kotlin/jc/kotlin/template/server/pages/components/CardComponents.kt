package jc.kotlin.template.server.pages.components

import jc.kotlin.template.server.components.CardProps
import jc.kotlin.template.server.components.asCard
import jc.kotlin.template.server.components.cardAccent
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*

fun FlowContent.cardComponent() {
    div {
        classes = setOf("flex", "flex-col", "gap-4")

        asCard()
        cardAccent()

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
        section {

            asCard()
            cardAccent()

            +"This is a standard card... cardception..."
        }
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-2", "gap-4")
            div {
                val cardProps = CardProps(accentColor = Color.Mint)
                asCard(cardProps)
                cardAccent(cardProps)
                +"Mint"
            }
            div {
                val cardProps = CardProps(accentColor = Color.Peach)
                asCard(cardProps)
                cardAccent(cardProps)
                +"Peach"
            }
            div {
                val cardProps = CardProps(accentColor = Color.Purple)
                asCard(cardProps)
                cardAccent(cardProps)
                +"Purple"
            }
        }
    }
}