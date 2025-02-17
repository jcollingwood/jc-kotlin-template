package jc.kotlin.template.app.config

val PORT = System.getenv("PORT")?.toInt() ?: 8081
val GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID")
val GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET")
