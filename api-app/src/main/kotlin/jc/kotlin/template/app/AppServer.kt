package jc.kotlin.template.app

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import jc.kotlin.template.app.auth.authModule
import jc.kotlin.template.app.config.CoreServices
import jc.kotlin.template.app.config.PORT

fun Application.appModule(core: CoreServices) {
    errorHandler()
    configureDatabase()
    authModule(core)
    appRoutes(core)
}

fun main() {
    embeddedServer(Netty, PORT) {
        appModule(CoreServices())
    }.start(wait = true)
}
