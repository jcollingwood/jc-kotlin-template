package jc.kotlin.template.server.session

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import jc.kotlin.template.server.auth.LOGIN_ROUTE
import jc.kotlin.template.server.auth.SESSION_DATA
import jc.kotlin.template.server.auth.SessionCookie

class SessionValidationConfig {
    lateinit var sessionService: SessionService
    var redirectOnFailure: Boolean = true
    var requireValidToken: Boolean = true
}

val SessionValidationPlugin = createRouteScopedPlugin(
    name = "SessionValidation",
    createConfiguration = ::SessionValidationConfig
) {
    val sessionService = pluginConfig.sessionService
    val redirectOnFailure = pluginConfig.redirectOnFailure
    val requireValidToken = pluginConfig.requireValidToken

    onCall { call ->
        val sessionCookie = call.sessions.get<SessionCookie>()
            ?: if (redirectOnFailure) {
                call.respondRedirect("/login")
                return@onCall
            } else {
                call.respond(HttpStatusCode.Unauthorized, "No session found")
                return@onCall
            }

        // Validate session against database
        val sessionData = sessionService.validateSession(sessionCookie.sessionToken)

        when {
            sessionData == null -> {
                call.sessions.clear<SessionCookie>()
                if (redirectOnFailure) {
                    call.respondRedirect("/login?error=invalid_session")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid session")
                }
                return@onCall
            }

            requireValidToken && sessionData.expiresAt < System.currentTimeMillis() -> {
                // Try to refresh the token
                call.sessions.clear<SessionCookie>()
                // TODO refresh token logic, redirecting to login in meantime
                call.respondRedirect(LOGIN_ROUTE)
                return@onCall
//                val refreshedSession = sessionService.refreshTokenIfNeeded(sessionData)
//                if (refreshedSession == null) {
//                    call.sessions.clear<SessionCookie>()
//                    if (redirectOnFailure) {
//                        call.respondRedirect("/login?error=token_expired")
//                    } else {
//                        call.respond(HttpStatusCode.Unauthorized, "Token expired")
//                    }
//                    return@onCall
//                }
//                // Store refreshed session data
//                call.attributes.put(SessionDataKey, refreshedSession)
            }

            else -> {
                // Store valid session data in call attributes
                call.attributes.put(SESSION_DATA, sessionData)
            }
        }
    }
}