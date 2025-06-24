package jc.kotlin.template.server.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import jc.kotlin.template.server.components.appHead
import jc.kotlin.template.server.components.buttonStyles
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.GOOGLE_CLIENT_ID
import jc.kotlin.template.server.config.GOOGLE_CLIENT_SECRET
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.main
import mu.two.KotlinLogging
import kotlin.collections.set


fun Application.authModule(core: CoreServices) {
    install(Sessions) {
        cookie<UserSession>(SESSION_COOKIE_KEY) {
            cookie.secure = false
        }
    }
    install(Authentication) {
        oauth(OAUTH_KEY) {
            // Configure oauth authentication
            urlProvider = { "${ROOT_DOMAIN}/callback" }
//                {
//                val log = KotlinLogging.logger {}
//                val url = "${ROOT_DOMAIN}/callback"
//                log.info("OAuth callback URL configured as: $url")
//                log.info("ROOT_DOMAIN value: $ROOT_DOMAIN")
//                url
//            }
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
                        val log = KotlinLogging.logger {}
                        log.info("State created: $state")
                        //saves new state with redirect url value
                        call.request.queryParameters["redirectUrl"]?.let {
                            log.info("Saving redirect for state $state: $it")
                            redirects[state] = it
                            log.info("Stored redirect - State: $state, Redirect: $it, Total redirects: ${redirects.size}")
                        }
                    }
                )
            }
        }
    }
    intercept(ApplicationCallPipeline.Plugins) {
        if (call.request.uri.contains("/callback")) {
            val log = KotlinLogging.logger {}
            log.info("Intercepting callback")
            log.info("URI: ${call.request.uri}")
            log.info("Method: ${call.request.httpMethod}")
            log.info("Headers: ${call.request.headers.entries()}")
            log.info("Query params: ${call.request.queryParameters.entries()}")
            return@intercept
        }
    }
    authRouting()
}

// auth login flow
fun Application.authRouting() {
    val log = KotlinLogging.logger {}

    /* services init */
    routing {
        get("/") {
            log.info("Login Page")
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
                        a(MANUAL_LOGIN_ROUTE) {
                            classes = buttonStyles
                            +"Login with Google Also"
                        }
                    }
                }
            }
        }
        get(MANUAL_LOGIN_ROUTE) {
            log.info("=== MANUAL LOGIN! ===")
            val authUrl = "https://accounts.google.com/o/oauth2/auth?" +
                    "client_id=$GOOGLE_CLIENT_ID" +
                    "&scope=https://www.googleapis.com/auth/userinfo.profile" +
                    "&response_type=code" +
                    "&access_type=offline" +
                    "&redirect_uri=${ROOT_DOMAIN}/callback"
            call.respondRedirect(authUrl)
        }

        authenticate(OAUTH_KEY) {
            get(LOGIN_ROUTE) {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/callback") {
                log.info("=== CALLBACK! ===")
                log.info("Callback received with parameters: ${call.request.queryParameters}")
                val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                log.info("OAuth principal: $currentPrincipal")

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