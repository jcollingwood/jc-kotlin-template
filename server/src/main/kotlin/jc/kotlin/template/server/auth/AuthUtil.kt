package jc.kotlin.template.server.auth

import io.ktor.util.*
import jc.kotlin.template.server.config.DOMAIN
import kotlinx.serialization.Serializable

const val OAUTH_KEY = "auth-oauth-google"

// TODO understand cookie stuff better
const val SESSION_COOKIE_KEY = "sesh"
val SESSION_DATA = AttributeKey<SessionCookie>("sesh_data")

@Serializable
data class SessionCookie(
    val sessionToken: String,
    val userId: String,
    val expiresAt: Long
)

const val LOGIN_ROUTE = "/login"
const val LANDING_ROUTE = "/welcome"

val ROOT_DOMAIN = DOMAIN

// map of redirects
val redirects = mutableMapOf<String, String>()
