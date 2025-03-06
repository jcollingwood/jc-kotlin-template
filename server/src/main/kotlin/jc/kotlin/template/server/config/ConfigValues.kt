package jc.kotlin.template.server.config

val PORT = System.getenv("PORT")?.toInt() ?: 3333
val GOOGLE_CLIENT_ID = System.getenv("JC_TEMPLATE_GOOGLE_CLIENT_ID")
val GOOGLE_CLIENT_SECRET = System.getenv("JC_TEMPLATE_GOOGLE_CLIENT_SECRET")
