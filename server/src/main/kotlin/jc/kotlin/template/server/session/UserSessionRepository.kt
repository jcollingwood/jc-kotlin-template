package jc.kotlin.template.server.session

import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.DB_TYPE
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserSessionEntity @OptIn(ExperimentalUuidApi::class) constructor(
    @Contextual
    val id: Uuid,
    @Serial
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

fun sessionRepositoryFactory(
    core: CoreServices
): UserSessionRepository {
    return when (DB_TYPE) {
        "memorymap" -> MapUserSessionRepository()
        "sqlite" -> DbUserSessionRepository(core)
        "postgres" -> DbUserSessionRepository(core)
        else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
    }
}

@OptIn(ExperimentalUuidApi::class)
class DbUserSessionRepository(
    private val core: CoreServices
) : UserSessionRepository {

    override suspend fun getSession(sessionToken: String): UserSessionEntity? {
        return core.database.getConnection().prepareStatement(
            """
            SELECT * FROM user_sessions WHERE session_token = $sessionToken
        """.trimIndent()
        )
            .use { stmt ->
                val result = stmt.executeQuery()
                if (result.next()) {
                    UserSessionEntity(
                        id = Uuid.fromByteArray(result.getString("id").encodeToByteArray()),
                        userId = result.getString("user_id"),
                        sessionToken = result.getString("session_token"),
                        accessTokenEncrypted = result.getString("access_token_encrypted"),
                        refreshTokenEncrypted = result.getString("refresh_token_encrypted"),
                        tokenExpiresAt = result.getLong("token_expires_at"),
                        sessionExpiresAt = result.getLong("session_expires_at"),
                        createdAt = result.getLong("created_at"),
                        lastAccessedAt = result.getLong("last_accessed_at")
                    )
                } else null
            }
    }

    override suspend fun createSession(entity: UserSessionEntity): UserSessionEntity {
        return core.userSessionDao.createSession(entity)
    }

    override suspend fun updateSession(entity: UserSessionEntity): UserSessionEntity {
        return core.userSessionDao.updateSession(entity)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateSessionLastAccessedAt(sessionToken: String, lastAccessedAt: Long): UserSessionEntity {
        return core.userSessionDao.updateSessionLastAccessedAt(sessionToken, lastAccessedAt)
    }

    override suspend fun deleteSession(sessionToken: String): Boolean {
        return core.userSessionDao.deleteSession(sessionToken)
    }

    override suspend fun deleteExpiredSessions(currentTime: Long): Int {
        return core.userSessionDao.deleteExpiredSessions(currentTime)
    }
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