package jc.kotlin.template.server.session

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import jc.kotlin.template.server.auth.SessionCookie
import jc.kotlin.template.server.user.UserEntity

val ADMIN_USER_ATTR = AttributeKey<UserEntity>("admin_user_entity")

class AdminSessionValidationConfig {
    lateinit var sessionService: SessionService
}

val AdminSessionValidationPlugin = createRouteScopedPlugin(
    name = "AdminSessionValidation",
    createConfiguration = ::AdminSessionValidationConfig
) {
    val sessionService = pluginConfig.sessionService

    onCall { call ->
        val sessionCookie = call.sessions.get<SessionCookie>()
            ?: run {
                call.respondRedirect("/login")
                return@onCall
            }

        // get current session user
        val userEntity = sessionService.getSessionUser(sessionCookie)

        when {
            !userEntity.isAdmin -> {
                call.respondRedirect("/welcome")
                return@onCall
            }

            else -> {
                // store user entity in call attributes for later use
                call.attributes.put(ADMIN_USER_ATTR, userEntity)
            }
        }
    }
}