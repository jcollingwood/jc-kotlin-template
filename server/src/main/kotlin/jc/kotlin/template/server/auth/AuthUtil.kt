package jc.kotlin.template.server.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import jc.kotlin.template.server.config.DOMAIN
import kotlinx.serialization.Serializable

//@Serializable
//data class UserSession(val state: String, val token: String)

@Serializable
data class UserSession(
    val userId: String,
    val email: String,
    val accessTokenHash: String? = null, // Store hash, not actual token
    val refreshTokenHash: String? = null,
    val expiresAt: Long,
)

const val OAUTH_KEY = "auth-oauth-google"

// TODO understand cookie stuff better
const val SESSION_COOKIE_KEY = "sesh"
const val LOGIN_ROUTE = "/login"
const val MANUAL_LOGIN_ROUTE = "/manual-login"
const val LANDING_ROUTE = "/welcome"

val ROOT_DOMAIN = DOMAIN

// map of redirects
val redirects = mutableMapOf<String, String>()

/*
 * gets the session from the cookie - redirects to login if there is no session
 */
suspend fun getSession(
    call: ApplicationCall
): UserSession? {
    val userSession: UserSession? = call.sessions.get(SESSION_COOKIE_KEY) as UserSession?
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
