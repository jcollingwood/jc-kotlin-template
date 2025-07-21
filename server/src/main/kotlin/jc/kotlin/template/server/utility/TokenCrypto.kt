package jc.kotlin.template.server.utility

import jc.kotlin.template.server.config.CRYPTO_SECRET_KEY
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun String.encrypt(): String {
    return TokenCrypto.encrypt(this)
}

fun String.decrypt(): String {
    return TokenCrypto.decrypt(this)
}

object TokenCrypto {
    private val SECRET = CRYPTO_SECRET_KEY // 16 chars for AES-128
    private val keySpec = SecretKeySpec(SECRET.toByteArray(), "AES")

    fun encrypt(token: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(token.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(token: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decoded = Base64.getDecoder().decode(token)
        return String(cipher.doFinal(decoded))
    }
}