package jc.kotlin.template.server.pages

import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.user.UserEntity
import kotlinx.html.FlowContent
import kotlinx.html.h1
import kotlinx.html.img
import kotlinx.html.p

val adminPage = Page("/admin", "Admin Page")
fun FlowContent.adminPage(user: UserEntity) {
    h1(classes = "text-2xl") { +"Admin Page" }
    img(src = user.picture, alt = "User Picture", classes = "rounded-full w-16 h-16")
    p { +"You are logged in as ${user.name}" }
    p { +"Your user ${if (user.isAdmin) "is" else "is not"} an admin" }
    p { +"TODO add admin-y things" }
}