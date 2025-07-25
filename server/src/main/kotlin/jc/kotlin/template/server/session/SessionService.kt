package jc.kotlin.template.server.session

import jc.kotlin.template.server.auth.SessionCookie
import jc.kotlin.template.server.auth.UserInfo
import jc.kotlin.template.server.user.UserEntity
import jc.kotlin.template.server.user.UserRepository
import jc.kotlin.template.server.utility.decrypt
import jc.kotlin.template.server.utility.encrypt
import java.util.*
import kotlin.time.Duration.Companion.hours

class SessionService(
    private val userRepo: UserRepository,
    private val userSessionRepo: UserSessionRepository
) {
    suspend fun createSession(
        userInfo: UserInfo,
        accessToken: String,
        refreshToken: String?,
    ): SessionCookie {
        val sessionToken = UUID.randomUUID().toString()
        val sessionExpiry = System.currentTimeMillis() + 24.hours.inWholeMilliseconds
        val tokenExpiry = System.currentTimeMillis() + 1.hours.inWholeMilliseconds

        // ignoring any kind of transaction management for simplicity
        userRepo.createUser(
            UserEntity(
                id = userInfo.id,
                name = userInfo.name,
                email = userInfo.name, // TODO
                picture = userInfo.picture,
                isAdmin = false,
                isActive = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )

        userSessionRepo.createSession(
            UserSessionEntity(
                userId = userInfo.id,
                sessionToken = sessionToken,
                accessTokenEncrypted = accessToken.encrypt(),
                refreshTokenEncrypted = refreshToken?.encrypt(),
                tokenExpiresAt = tokenExpiry,
                sessionExpiresAt = sessionExpiry,
                createdAt = System.currentTimeMillis(),
                lastAccessedAt = System.currentTimeMillis()
            )
        )

        return SessionCookie(
            sessionToken = sessionToken,
            userId = userInfo.id,
            expiresAt = sessionExpiry
        )
    }

    suspend fun getSessionAccessToken(sessionToken: String): String? {
        val entity = userSessionRepo.getSession(sessionToken) ?: return null
        if (entity.sessionExpiresAt < System.currentTimeMillis()) {
            return null
        }

        // Update last accessed time
        userSessionRepo.updateSessionLastAccessedAt(
            sessionToken = sessionToken,
            lastAccessedAt = System.currentTimeMillis()
        )

        return entity.accessTokenEncrypted.decrypt()
    }

    suspend fun validateSession(sessionToken: String): SessionCookie? {
        val entity = userSessionRepo.getSession(sessionToken) ?: return null
        if (entity.sessionExpiresAt < System.currentTimeMillis()) {
            return null
        }

        // If the session is valid, update the last accessed time
        userSessionRepo.updateSessionLastAccessedAt(
            sessionToken = sessionToken,
            lastAccessedAt = System.currentTimeMillis()
        )

        return SessionCookie(
            sessionToken = entity.sessionToken,
            userId = entity.userId,
            expiresAt = entity.sessionExpiresAt
        )
    }

    suspend fun pruneExpiredSessions() {
        val currentTime = System.currentTimeMillis()
        userSessionRepo.deleteExpiredSessions(currentTime)
    }
}