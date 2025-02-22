package jc.kotlin.template.app.auth

import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.oauth
import io.ktor.server.auth.principal
import io.ktor.server.html.respondHtml
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.sessions
import jc.kotlin.template.app.config.CoreServices
import jc.kotlin.template.app.config.GOOGLE_CLIENT_ID
import jc.kotlin.template.app.config.GOOGLE_CLIENT_SECRET
import jc.kotlin.template.app.htmx
import jc.kotlin.template.app.matIcons
import jc.kotlin.template.app.stylesAndFonts
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

fun Application.authModule(core: CoreServices) {
    install(Sessions) {
        cookie<UserSession>(SESSION_COOKIE_KEY)
    }
    install(Authentication) {
        oauth(OAUTH_KEY) {
            // Configure oauth authentication
            urlProvider = { "${ROOT_DOMAIN}/callback" }
            client = core.httpClient
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = GOOGLE_CLIENT_ID,
                    clientSecret = GOOGLE_CLIENT_SECRET,
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                    extraAuthParameters = listOf("access_type" to "offline"),
                    onStateCreated = { call, state ->
                        //saves new state with redirect url value
                        call.request.queryParameters["redirectUrl"]?.let {
                            redirects[state] = it
                        }
                    }
                )
            }
        }
    }
    authRouting()
}

// auth login flow
fun Application.authRouting() {
    /* services init */
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"Login" }
                    stylesAndFonts()
                    matIcons()
                    htmx()
                }
                body {
                    p {
                        a(LOGIN_ROUTE) { +"Login with Google" }
                    }
                }
            }
        }

        authenticate(OAUTH_KEY) {
            get(LOGIN_ROUTE) {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/callback") {
                val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                // redirects home if the url is not found before authorization
                currentPrincipal?.let { principal ->
                    principal.state?.let { state ->
                        val userSession = UserSession(state, principal.accessToken)
                        call.sessions.set(SESSION_COOKIE_KEY, userSession)
                        redirects[state]?.let { redirect ->
                            call.respondRedirect(redirect)
                            return@get
                        }
                    }
                }
                call.respondRedirect(LANDING_ROUTE)
            }
        }
    }
}