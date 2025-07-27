package jc.kotlin.template.server.pages

import jc.kotlin.template.server.auth.SessionCookie
import jc.kotlin.template.server.components.jcCard
import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.user.UserEntity
import kotlinx.html.*

val welcomePage = Page("/welcome", "Welcome")

fun FlowContent.welcomePage(userEntity: UserEntity, sessionCookie: SessionCookie) {
    // figure out expiresAt long to get expiration duration in minutes
    val expireMillis = sessionCookie.expiresAt - System.currentTimeMillis()

    h1(classes = "text-2xl") { +"Welcome" }
    img(src = userEntity.picture, alt = "User Picture", classes = "rounded-full w-16 h-16")
    p { +"You are logged in as ${userEntity.name}" }
    p { +"Your user ${if (userEntity.isAdmin) "is" else "is not"} an admin" }
    p { +"Session expires in ~${expireMillis / 1000 / 60 / 60} hours" }
    div {
        classes = setOf("max-w-2xl")
        jcCard {
            div {
                classes = setOf("flex", "flex-col", "gap-2")
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
}
