package com.thebookstore.security

import com.thebookstore.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(customerId: Int): String {
        return Jwts.builder()
            .setSubject(customerId.toString())
            .setExpiration(Date(System.currentTimeMillis() + expiration!!))
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
            .compact()
    }

    fun isValid(token: String): Boolean {
        val claims = getClaims(token)
        if (claims != null) {
            val customerId = claims.subject
            val expirationDate = claims.expiration
            val now = Date(System.currentTimeMillis())
            if (customerId != null && expirationDate != null && now.before(expirationDate)) {
                return true
            }
        }
        return false
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw AuthenticationException("Token inválido", "999")
        }
    }

    fun getCustomerId(token: String): String {
        val claims = getClaims(token)
        return claims.subject
    }
}