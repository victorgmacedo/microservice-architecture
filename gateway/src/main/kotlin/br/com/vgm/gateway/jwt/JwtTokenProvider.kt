package br.com.vgm.gateway.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class JwtTokenProvider(
    @Value("\${security.jwt.token.secret-key}")
    private var secretKey: String
) {

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    private fun getHeaderClaimsJwt(token: String): Jws<Claims> {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getHeaderClaimsJwt(token).body.expiration.after(Date())
        } catch (ex: Exception) {
            false
        }
    }

    fun getAllClaimsFromToken(token: String): Claims {
        return getHeaderClaimsJwt(token).body
    }
}