package jc.kotlin.template.server.config

val PORT = System.getenv("PORT")?.toInt() ?: 3333
val DOMAIN = System.getenv("JC_TEMPLATE_DOMAIN") ?: "http://localhost:$PORT"

// auth config
val GOOGLE_CLIENT_ID = System.getenv("JC_TEMPLATE_GOOGLE_CLIENT_ID")
val GOOGLE_CLIENT_SECRET = System.getenv("JC_TEMPLATE_GOOGLE_CLIENT_SECRET")

// persistence config
val DB_TYPE = System.getenv("JC_TEMPLATE_DB_TYPE") ?: "memorymap" // memorymap or sqlite or postgres
val SQLITE_CONN_URL = System.getenv("SQLITE_CONN_URL") ?: "jdbc:sqlite:./jc-kotlin-template"
val PSQL_CONN_URL = System.getenv("PSQL_CONN_URL") ?: "jdbc:postgresql://localhost:5432/template_db"
