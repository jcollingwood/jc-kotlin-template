package jc.kotlin.template.server.config

import com.zaxxer.hikari.HikariDataSource

object Database { // singleton object for database connection management
    val dataSource: HikariDataSource

    init {
        val config = HikariDataSource().apply {
            jdbcUrl = when (DB_TYPE) {
                "sqlite" -> SQLITE_CONN_URL
                "postgres" -> PSQL_CONN_URL
                else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
            }
            driverClassName = when (DB_TYPE) {
                "sqlite" -> "org.sqlite.JDBC"
                "postgres" -> "org.postgresql.Driver"
                else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
            }
            maximumPoolSize = when (DB_TYPE) {
                "sqlite" -> 1 // SQLite is single-threaded
                "postgres" -> 10 // Adjust based on your needs
                else -> throw IllegalArgumentException("Unsupported DB_TYPE: $DB_TYPE")
            }
            isAutoCommit = true
        }
        dataSource = HikariDataSource(config)
    }

    private fun getConnection() = dataSource.connection
    fun <T> useConnection(block: (java.sql.Connection) -> T): T {
        return getConnection().use { connection ->
            block(connection)
        }
    }

    fun close() = dataSource.close()
}