package jc.kotlin.template.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.PORT

fun main() {
    embeddedServer(factory = Netty, port = PORT) {
        appModule(CoreServices())
        apiModule()
    }.start(wait = true)
}
