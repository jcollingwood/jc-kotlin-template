package jc.kotlin.template.app

import io.ktor.server.application.Application
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import jc.kotlin.template.app.auth.UserInfoService
import jc.kotlin.template.app.auth.UserSession
import jc.kotlin.template.app.auth.getSession
import jc.kotlin.template.app.config.CoreServices
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.main
import kotlinx.html.p
import kotlinx.html.title
import java.io.File

fun Application.appRoutes(core: CoreServices) {
    val userInfoService = UserInfoService(core)
    routing {
        // static directory route relative to project root dir, should pull in tailwind css
        staticFiles("/static", File("api-app/src/main/resources/static"))

        route("/welcome") {
            get {
                val userSession: UserSession? = getSession(call)

                if (userSession != null) {
                    val userInfo = userInfoService.getUserInfo(call, userSession)

                    call.respondHtml {
                        head {
                            title { +"Welcome" }
                            stylesAndFonts()
                            matIcons()
                            htmx()
                        }
                        body {
                            main {
                                classes =
                                    setOf(
                                        "font-inter", "flex", "flex-col", "h-full", "w-screen", "items-center", "p-4"
                                    )
                                h1(classes = "text-2xl") { +"Welcome" }
                                p { +"You are logged in as ${userInfo.name}" }
                            }
                        }
                    }
                }
            }
        }
    }
}

