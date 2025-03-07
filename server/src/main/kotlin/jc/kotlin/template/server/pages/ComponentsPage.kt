package jc.kotlin.template.server.pages

import jc.kotlin.template.server.components.hxGet
import jc.kotlin.template.server.components.hxSwap
import jc.kotlin.template.server.components.hxTarget
import jc.kotlin.template.server.components.jcButton
import jc.kotlin.template.server.routes.Page
import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.id
import kotlinx.html.p


val componentsPage = Page("/components", "Components")
fun FlowContent.componentsPage() {
    h1(classes = "text-2xl") { +"Components" }
    div {
        classes = setOf("flex", "flex-col", "w-full", "items-center", "mt-4", "gap-4")
        div {
            classes = setOf("grid", "grid-cols-1", "sm:grid-cols-3", "gap-4", "items-center")
            jcButton {
                hxGet("/components/button")
                hxTarget("#component-area")
                hxSwap("innerHTML")

                +"Button"
            }
            jcButton {
                +"Input"
            }
        }
        div {
            id = "component-area"
            classes = setOf("w-1/2", "p-4", "py-12", "border", "border-gray-200", "rounded-md", "items-center")
            p {
                classes = setOf("text-center")
                +"Select a component to test"
            }
        }
    }
}

fun FlowContent.buttonComponent() {
    div {
        classes = setOf("grid", "grid-cols-1", "sm:grid-cols-2", "w-full", "items-center", "gap-4")
        jcButton {
            +"Default"
        }
        jcButton(setOf("border-red-400", "bg-red-100", "hover:bg-red-500", "hover:text-white")) {
            +"Snazzy Styles"
        }
        jcButton(setOf("hover:bg-green-100")) {
            +"Green Hover"
        }
        jcButton(setOf("font-bold", "text-24")) {
            +"Large and Bold"
        }
        jcButton(setOf("rounded-full")) {
            +"Rounded"
        }
        jcButton(setOf("bg-black", "text-white", "hover:bg-gray-500", "rounded-none")) {
            +"Serious"
        }
    }
}