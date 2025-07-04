package jc.kotlin.template.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.PORT

fun main() {
    embeddedServer(factory = Netty, port = PORT) {
        val coreServices = CoreServices()
        val userInfoService = UserInfoService(coreServices)
        appModule(core = coreServices, userInfoService = userInfoService)
        apiModule()
    }.start(wait = true)
}
