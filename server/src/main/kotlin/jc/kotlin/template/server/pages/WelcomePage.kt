package jc.kotlin.template.server.pages

import jc.kotlin.template.server.auth.SessionCookie
import jc.kotlin.template.server.components.CardProps
import jc.kotlin.template.server.components.asCard
import jc.kotlin.template.server.components.cardAccent
import jc.kotlin.template.server.components.titleAccent
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.user.UserEntity
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*

val welcomePage = Page("/welcome", "Welcome")

fun FlowContent.welcomePage(userEntity: UserEntity, sessionCookie: SessionCookie) {
    // figure out expiresAt long to get expiration duration in minutes
    val expireMillis = sessionCookie.expiresAt - System.currentTimeMillis()

    div {
        classes = setOf("flex", "flex-col", "gap-6", "w-full", "max-w-2xl", "font-xl")

        h1(classes = "header text-xl relative") {
            titleAccent()
            +"Welcome"
        }
        section {
            val cardProps = CardProps(
                accentColor = Color.Purple,
                classes = setOf("flex", "flex-col", "gap-4")
            )
            asCard(props = cardProps)
            cardAccent(props = cardProps)
            div {
                classes = setOf("flex", "flex-col", "sm:flex-row", "items-center", "gap-4")
                div {
                    img(src = userEntity.picture, alt = "User Picture", classes = "rounded-full w-24 h-24")
                }
                div {
                    classes = setOf("flex", "flex-col", "gap-2")
                    p { +"You are logged in as ${userEntity.name}" }
                    p { +"Your user ${if (userEntity.isAdmin) "is" else "is not"} an admin" }
                    p { +"Session expires in ~${expireMillis / 1000 / 60 / 60} hours" }
                }
            }
        }

        section {
            val cardProps = CardProps(
                accentColor = Color.Mint,
                classes = setOf("flex", "flex-col", "gap-3")
            )
            asCard(props = cardProps)
            cardAccent(props = cardProps)
            p { +"This is a template project for Kotlin web development with Ktor and Htmx." }
            p {
                +"""
                This project is intended to be a starting point for a fast 
                project setup with my updated and preferred tech stack.
                Experimentation on this isolated project allows me to test new 
                technologies as well as upgrades in a core project before adopting 
                across all projects using this core tech stack.
                """
            }
        }
    }
}
