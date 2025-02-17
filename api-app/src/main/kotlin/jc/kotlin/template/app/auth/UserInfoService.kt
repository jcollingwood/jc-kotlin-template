package jc.kotlin.template.app.auth

import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondRedirect
import jc.kotlin.template.app.config.CoreServices
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mu.two.KotlinLogging

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
    private val core: CoreServices
) {
    companion object {
        val log = KotlinLogging.logger {}
    }

    fun getUserInfo(call: ApplicationCall, userSession: UserSession): UserInfo {
        return core.jsonParser.decodeFromString<UserInfo>(lookupUser(call, userSession))
    }

    fun lookupUser(call: ApplicationCall, c: UserSession): String {
        return runBlocking {
            val response = core.httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${c.token}")
                }
            }

            if (response.status != HttpStatusCode.OK) {
                log.debug("failed to retrieve user info. status: ${response.status}")
                call.respondRedirect(ROOT_DOMAIN + LOGIN_ROUTE)
            }

            return@runBlocking response.bodyAsText()
        }
    }
}
