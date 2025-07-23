package jc.kotlin.template.server.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.session.SessionService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name")
    val givenName: String,
    @SerialName("family_name")
    val familyName: String,
    val picture: String
)

class UserInfoService(
    private val core: CoreServices,
    private val sessionService: SessionService
) {
    companion object {
        val log = KotlinLogging.logger {}
    }

    suspend fun createUserSession(call: ApplicationCall, accessToken: String, refreshToken: String?): SessionCookie {
        val userInfo = getUserInfo(call, accessToken)
        val session = sessionService.createSession(
            userId = userInfo.id,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
        return session
    }

    fun getUserInfoFromSession(call: ApplicationCall, session: SessionCookie): UserInfo {
        return runBlocking {
            // TODO handle null
            val accessToken: String = sessionService.getSessionAccessToken(session.sessionToken) ?: ""
            return@runBlocking getUserInfo(
                call,
                accessToken
            )
        }
    }

    private fun getUserInfo(call: ApplicationCall, accessToken: String): UserInfo {
        return core.jsonParser.decodeFromString<UserInfo>(lookupUser(call, accessToken))
    }

    private fun lookupUser(call: ApplicationCall, accessToken: String): String {
        return runBlocking {
            val response = core.httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }

            if (response.status != HttpStatusCode.OK) {
                log.debug { "failed to retrieve user info. status: ${response.status}" }
                call.respondRedirect(ROOT_DOMAIN + LOGIN_ROUTE)
            }

            return@runBlocking response.bodyAsText()
        }
    }
}
