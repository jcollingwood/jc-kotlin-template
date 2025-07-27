package jc.kotlin.template.server.user

import jc.kotlin.template.server.config.CoreServices
import jc.kotlin.template.server.config.DB_TYPE
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: String,
    val name: String,
    val email: String? = null,
    val picture: String? = null,
    val isAdmin: Boolean = false,
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

interface UserRepository {
    suspend fun getUserById(id: String): UserEntity?
    suspend fun createUser(user: UserEntity): UserEntity
    suspend fun deleteUser(id: String): Boolean
}

fun userRepositoryFactory(core: CoreServices): UserRepository {
    return when (DB_TYPE) {
        "sqlite" -> SQLiteUserRepository(core)
//        "postgres" -> DbUserRepository(core)
        else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
    }
}

class SQLiteUserRepository(
    private val core: CoreServices
) : UserRepository {
    override suspend fun getUserById(id: String): UserEntity? {
        return core.database.useConnection { conn ->
            conn.prepareStatement("SELECT * FROM users WHERE id = ?;")
                .use { stmt ->
                    stmt.setString(1, id)
                    val result = stmt.executeQuery()
                    if (result.next()) mapResultToUserEntity(result) else null
                }
        }
    }

    override suspend fun createUser(user: UserEntity): UserEntity {
        core.database.useConnection { conn ->
            conn.prepareStatement(
                """
               INSERT INTO users (
                    id, 
                    name, 
                    email,
                    picture,
                    is_admin,
                    is_active,
                    created_at,
                    updated_at
                ) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """.trimIndent()
            ).use { stmt ->
                stmt.setString(1, user.id)
                stmt.setString(2, user.name)
                stmt.setString(3, user.email)
                stmt.setString(4, user.picture)
                stmt.setBoolean(5, user.isAdmin)
                stmt.setBoolean(6, user.isActive)
                stmt.setLong(7, user.createdAt)
                stmt.setLong(8, user.updatedAt)
                stmt.executeUpdate()
            }
        }
        return user
    }

    override suspend fun deleteUser(id: String): Boolean {
        return core.database.useConnection { conn ->
            conn.prepareStatement("DELETE FROM users WHERE id = ?;").use { stmt ->
                stmt.setString(1, id)
                stmt.executeUpdate() > 0
            }
        }
    }

    private fun mapResultToUserEntity(resultSet: java.sql.ResultSet): UserEntity {
        return UserEntity(
            id = resultSet.getString("id"),
            name = resultSet.getString("name"),
            email = resultSet.getString("email"),
            picture = resultSet.getString("picture"),
            isAdmin = resultSet.getBoolean("is_admin"),
            isActive = resultSet.getBoolean("is_active"),
            createdAt = resultSet.getLong("created_at"),
            updatedAt = resultSet.getLong("updated_at")
        )
    }
}
