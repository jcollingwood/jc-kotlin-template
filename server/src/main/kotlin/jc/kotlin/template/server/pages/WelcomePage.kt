package jc.kotlin.template.server.pages

import jc.kotlin.template.server.auth.UserInfo
import jc.kotlin.template.server.components.jcCard
import jc.kotlin.template.server.routes.Page
import kotlinx.html.*

val welcomePage = Page("/welcome", "Welcome")

fun FlowContent.welcomePage(userInfo: UserInfo) {
    h1(classes = "text-2xl") { +"Welcome" }
    p { +"You are logged in as ${userInfo.name} (id ${userInfo.id})" }
    p { +"Session expires in ${userInfo.id}" }
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
