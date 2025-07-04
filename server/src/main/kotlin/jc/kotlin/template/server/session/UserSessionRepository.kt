package jc.kotlin.template.server.session

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserSessionEntity @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val userId: String,
    val sessionToken: String,
    val accessTokenEncrypted: String,
    val refreshTokenEncrypted: String? = null,
    val tokenExpiresAt: Long,
    val sessionExpiresAt: Long,
    val createdAt: Long,
    val lastAccessedAt: Long,
)

interface UserSessionRepository {
    suspend fun getSession(sessionToken: String): UserSessionEntity?
    suspend fun createSession(entity: UserSessionEntity): UserSessionEntity
    suspend fun updateSession(entity: UserSessionEntity): UserSessionEntity
    suspend fun updateSessionLastAccessedAt(sessionToken: String, lastAccessedAt: Long): UserSessionEntity
    suspend fun deleteSession(sessionToken: String): Boolean
    suspend fun deleteExpiredSessions(currentTime: Long): Int
}

// for testing using in memory map in place of db
class MapUserSessionRepository : UserSessionRepository {
    private val sessions = mutableMapOf<String, UserSessionEntity>()

    override suspend fun getSession(sessionToken: String): UserSessionEntity? {
        return sessions[sessionToken]
    }

    override suspend fun createSession(entity: UserSessionEntity): UserSessionEntity {
        sessions[entity.sessionToken] = entity
        return entity
    }

    override suspend fun updateSession(entity: UserSessionEntity): UserSessionEntity {
        sessions[entity.sessionToken] = entity
        return entity
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateSessionLastAccessedAt(sessionToken: String, lastAccessedAt: Long): UserSessionEntity {
        val session = sessions[sessionToken] ?: throw NoSuchElementException("Session not found")
        val updatedSession = session.copy(lastAccessedAt = lastAccessedAt)
        sessions[sessionToken] = updatedSession
        return updatedSession
    }

    override suspend fun deleteSession(sessionToken: String): Boolean {
        return sessions.remove(sessionToken) != null
    }

    override suspend fun deleteExpiredSessions(currentTime: Long): Int {
        val expiredSessions = sessions.values.filter { it.sessionExpiresAt < currentTime }
        expiredSessions.forEach { deleteSession(it.sessionToken) }
        return expiredSessions.size
    }
}