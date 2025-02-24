package jc.kotlin.template.app

import io.ktor.server.application.Application
import jc.kotlin.template.app.auth.authModule
import jc.kotlin.template.app.config.CoreServices
import jc.kotlin.template.app.config.errorHandler
import jc.kotlin.template.app.routes.appRoutes

fun Application.appModule(core: CoreServices) {
    errorHandler()
    configureDatabase()
    authModule(core)
    appRoutes(core)
}
