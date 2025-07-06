package jc.kotlin.template.server.session

import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.DB_TYPE
import kotlinx.serialization.Serializable
import mu.two.KotlinLogging

@Serializable
data class UserSessionEntity(
    val id: Int = 0, // Default to 0 for new entities
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
    suspend fun createSession(entity: UserSessionEntity)
    suspend fun updateSessionLastAccessedAt(sessionToken: String, lastAccessedAt: Long)
    suspend fun deleteSession(sessionToken: String): Boolean
    suspend fun deleteExpiredSessions(currentTime: Long): Int
}

fun sessionRepositoryFactory(
    core: CoreServices
): UserSessionRepository {
    return when (DB_TYPE) {
        "sqlite" -> SQLiteUserSessionRepository(core)
//        "postgres" -> DbUserSessionRepository(core)
        else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
    }
}

class SQLiteUserSessionRepository(
    private val core: CoreServices
) : UserSessionRepository {
    val log = KotlinLogging.logger {}

    override suspend fun getSession(sessionToken: String): UserSessionEntity? {
        return core.database.useConnection { conn ->
            conn.prepareStatement("SELECT * FROM user_sessions WHERE session_token = ?;")
                .use { stmt ->
                    stmt.setString(1, sessionToken)
                    val result = stmt.executeQuery()
                    if (result.next()) mapResultToUserSessionEntity(result)
                    else null
                }
        }
    }

    override suspend fun createSession(entity: UserSessionEntity) {
        log.info("Saving session for token: ${entity.userId} | ${entity.sessionToken}")
        return core.database.useConnection { conn ->
            conn.prepareStatement(
                """
            INSERT INTO user_sessions (
                user_id, 
                session_token, 
                access_token_encrypted, 
                refresh_token_encrypted, 
                token_expires_at, 
                session_expires_at, 
                created_at, 
                last_accessed_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """.trimIndent()
            )
                .use { stmt ->
                    stmt.setString(1, entity.userId)
                    stmt.setString(2, entity.sessionToken)
                    stmt.setString(3, entity.accessTokenEncrypted)
                    stmt.setString(4, entity.refreshTokenEncrypted)
                    stmt.setLong(5, entity.tokenExpiresAt)
                    stmt.setLong(6, entity.sessionExpiresAt)
                    stmt.setLong(7, entity.createdAt)
                    stmt.setLong(8, entity.lastAccessedAt)

                    val result = stmt.executeUpdate()
                    if (result != 1) {
                        throw IllegalStateException("Failed to create session for user: ${entity.userId}")
                    }
                }
        }
    }

    override suspend fun updateSessionLastAccessedAt(sessionToken: String, lastAccessedAt: Long) {
        return core.database.useConnection { conn ->
            conn.prepareStatement(
                """
            UPDATE user_sessions
            SET last_accessed_at = ?
            WHERE session_token = ?;
        """.trimIndent()
            )
                .use { stmt ->
                    stmt.setLong(1, lastAccessedAt)
                    stmt.setString(2, sessionToken)

                    val result = stmt.executeUpdate()
                    if (result != 1) throw NoSuchElementException("Session not found")
                }
        }
    }

    override suspend fun deleteSession(sessionToken: String): Boolean {
        return core.database.useConnection { conn ->
            conn.prepareStatement(
                """
            DELETE FROM user_sessions
            WHERE session_token = ?;
        """.trimIndent()
            )
                .use { stmt ->
                    stmt.setString(1, sessionToken)
                    val result = stmt.executeUpdate()
                    if (result != 1) throw NoSuchElementException("Session not found") // If a row was deleted, this will return true
                    else true
                }
        }
    }

    override suspend fun deleteExpiredSessions(currentTime: Long): Int {
        return core.database.useConnection { conn ->
            conn.prepareStatement(
                """
            DELETE FROM user_sessions
            WHERE session_expires_at < ?;
        """.trimIndent()
            )
                .use { stmt ->
                    stmt.setLong(1, currentTime)
                    val result = stmt.executeUpdate()
                    result
                }
        }
    }

    private fun mapResultToUserSessionEntity(result: java.sql.ResultSet): UserSessionEntity {
        return UserSessionEntity(
            id = result.getInt("id"),
            userId = result.getString("user_id"),
            sessionToken = result.getString("session_token"),
            accessTokenEncrypted = result.getString("access_token_encrypted"),
            refreshTokenEncrypted = result.getString("refresh_token_encrypted"),
            tokenExpiresAt = result.getLong("token_expires_at"),
            sessionExpiresAt = result.getLong("session_expires_at"),
            createdAt = result.getLong("created_at"),
            lastAccessedAt = result.getLong("last_accessed_at")
        )
    }
}
