package jc.kotlin.template.server.config

import com.zaxxer.hikari.HikariDataSource

object Database { // singleton object for database connection management
    private val dataSource: HikariDataSource

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
            maximumPoolSize = 10
            isAutoCommit = false
        }
        dataSource = HikariDataSource(config)
    }

    fun getConnection() = dataSource.connection
    fun close() = dataSource.close()
}