package jc.kotlin.template.server

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.PORT

fun main() {
    embeddedServer(Netty, PORT) {
        appModule(CoreServices())
        apiModule()
    }.start(wait = true)
}
