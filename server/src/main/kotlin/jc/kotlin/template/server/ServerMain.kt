package jc.kotlin.template.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jc.kotlin.template.server.auth.UserInfoService
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.Database
import jc.kotlin.template.server.config.PORT
import jc.kotlin.template.server.session.SessionService
import jc.kotlin.template.server.session.sessionRepositoryFactory

fun main() {
    embeddedServer(factory = Netty, port = PORT) {
        val coreServices = CoreServices()
        val sessionService = SessionService(userSessionRepo = sessionRepositoryFactory(coreServices))
        val userInfoService = UserInfoService(core = coreServices, sessionService = sessionService)

        appModule(core = coreServices, userInfoService = userInfoService, sessionService = sessionService)
        apiModule()
    }
        .start(wait = true)
        .addShutdownHook { Database.close() }
}
