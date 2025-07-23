package jc.kotlin.template.server.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import jc.kotlin.template.server.components.appHead
import jc.kotlin.template.server.components.buttonStyles
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.GOOGLE_CLIENT_ID
import jc.kotlin.template.server.config.GOOGLE_CLIENT_SECRET
import jc.kotlin.template.server.session.SessionService
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.main
import kotlin.time.Duration.Companion.hours

fun Application.authModule(core: CoreServices, userInfoService: UserInfoService, sessionService: SessionService) {
    install(Sessions) {
        cookie<SessionCookie>(SESSION_COOKIE_KEY) {
            cookie.secure = true
            cookie.httpOnly = true
            cookie.maxAge = 2.hours
            cookie.encoding = CookieEncoding.BASE64_ENCODING
        }
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
    authRouting(userInfoService = userInfoService, sessionService = sessionService)
}

// auth login flow
fun Application.authRouting(userInfoService: UserInfoService, sessionService: SessionService) {
    val log = KotlinLogging.logger {}

    /* services init */
    routing {
        get {
            call.respondHtml {
                appHead("Login")
                body {
                    main {
                        classes = setOf(
                            "font-inter",
                            "flex",
                            "flex-col",
                            "h-full",
                            "w-screen",
                            "items-center",
                            "p-6"
                        )
                        a(LOGIN_ROUTE) {
                            classes = buttonStyles
                            +"Login with Google"
                        }
                    }
                }
            }
        }

        authenticate(OAUTH_KEY) {
            get(LOGIN_ROUTE) {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/callback") {
                val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()

                // redirects home if the url is not found before authorization
                currentPrincipal?.let { principal ->
                    principal.state?.let { state ->
                        val session =
                            userInfoService.createUserSession(call, principal.accessToken, principal.refreshToken)
                        call.sessions.set(SESSION_COOKIE_KEY, session)
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