package jc.kotlin.template.app.pages

import jc.kotlin.template.app.auth.UserInfo
import jc.kotlin.template.app.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.p

val welcomePage = Page("/welcome", "Welcome")

fun FlowContent.welcomePage(userInfo: UserInfo) {
    h1(classes = "text-2xl") { +"Welcome" }
    p { +"You are logged in as ${userInfo.name}" }
    div {
        classes =
            setOf("mt-4", "border", "p-4", "border-gray-200", "rounded-md", "max-w-2xl", "space-y-4")
        p { +"This is a template project for Kotlin web development with Ktor and Htmx." }
        p {
            +"""
                This project is intended to be a starting point for a fast project setup with my updated and preferred tech stack.
                Experimentation on this isolated project allows me to test new technologies as well as upgrades in a core project before adopting across all projects using this core tech stack.
            """
        }
    }
}
