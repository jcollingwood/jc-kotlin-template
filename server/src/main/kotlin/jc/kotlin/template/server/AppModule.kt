package jc.kotlin.template.server

import io.ktor.server.application.Application
import jc.kotlin.template.server.auth.authModule
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.errorHandler
import jc.kotlin.template.server.routes.appRoutes

fun Application.appModule(core: CoreServices) {
    errorHandler()
    configureDatabase()
    authModule(core)
    appRoutes(core)
}
