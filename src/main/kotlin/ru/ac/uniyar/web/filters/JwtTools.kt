package ru.ac.uniyar.web.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date

class JwtTools(
    secret: String,
    private val issuer: String,
    private val expiresIn: Period = Period.ofDays(DEFAULT_TOKEN_LIFE)
) {
    companion object {
        private const val ACCEPTED_LIFE_IN_SECONDS = 60L
        private const val DEFAULT_TOKEN_LIFE = 7
    }
    private val algorithm = Algorithm.HMAC512(secret)
    private val verifier by lazy {
        JWT
            .require(algorithm)
            .acceptExpiresAt(ACCEPTED_LIFE_IN_SECONDS)
            .build()
    }

    fun create(subject: String): String? {
        return try {
            JWT
                .create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withExpiresAt(
                    Date.from(
                        LocalDate
                            .now()
                            .plus(expiresIn)
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                    )
                )
                .sign(algorithm)
        } catch (_: JWTCreationException) {
            null
        }
    }

    val subject: (token: String) -> String? = {
        try {
            val decodedToken = verifier.verify(it)
            decodedToken.subject
        } catch (_: JWTVerificationException) {
            null
        }
    }
}
