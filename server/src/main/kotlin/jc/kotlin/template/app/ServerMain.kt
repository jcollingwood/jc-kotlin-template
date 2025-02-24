package jc.kotlin.template.app

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import jc.kotlin.template.app.config.CoreServices
import jc.kotlin.template.app.config.PORT

fun main() {
    embeddedServer(Netty, PORT) {
        appModule(CoreServices())
        apiModule()
    }.start(wait = true)
}
