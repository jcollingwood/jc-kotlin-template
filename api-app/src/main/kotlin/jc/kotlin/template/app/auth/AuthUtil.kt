package jc.kotlin.template.app.auth

import io.ktor.http.URLBuilder
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.uri
import io.ktor.server.response.respondRedirect
import io.ktor.server.sessions.sessions
import jc.kotlin.template.app.config.PORT
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val state: String, val token: String)

const val OAUTH_KEY = "auth-oauth-google"

// TODO understand cookie stuff better
const val SESSION_COOKIE_KEY = "sesh"
const val LOGIN_ROUTE = "/login"
const val LANDING_ROUTE = "/welcome"

// TODO externalize domain
val ROOT_DOMAIN = "http://localhost:${PORT}"

// map of redirects
val redirects = mutableMapOf<String, String>()

/*
 * gets the session from the cookie - redirects to login if there is no session
 */
suspend fun getSession(
    call: ApplicationCall
): UserSession? {
    val userSession: UserSession? = call.sessions.get(SESSION_COOKIE_KEY) as UserSession
    //if there is no session, redirect to login
    if (userSession == null) {
        val redirectUrl = URLBuilder(ROOT_DOMAIN + LOGIN_ROUTE).run {
            parameters.append("redirectUrl", call.request.uri)
            build()
        }
        call.respondRedirect(redirectUrl)
        return null
    }
    return userSession
}
