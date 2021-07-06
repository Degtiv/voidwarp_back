package space.degtiv.voidwarp.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import space.degtiv.voidwarp.helper.TimeHelper.Companion.convertLocalDateTimeToDate
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

const val AUTHORIZATION_HEADER = "token"
const val AUTH_TOKEN_PREFIX = "Bearer "

@Component
class JwtTokenProvider(
    @Value("\${jwt.token.secret.repeat}") private val secretRepeat: Int,
    @Value("\${jwt.token.secret.value}") var secretKey: String,
    @Value("\${jwt.token.expirationInSeconds}") private val expirationInSeconds: Long,
    passwordEncoder: PasswordEncoder
) {
    init {
        secretKey = passwordEncoder.encode(secretKey.repeat(secretRepeat))
    }

    fun createToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("authorities", authorities)
            .setIssuedAt(convertLocalDateTimeToDate(LocalDateTime.now()))
            .setExpiration(convertLocalDateTimeToDate(LocalDateTime.now().plusSeconds(expirationInSeconds)))
            .signWith(Keys.hmacShaKeyFor(secretKey.encodeToByteArray()))
            .compact()
    }

    fun getUsername(token: String): String {
        return getClaims(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val authHeader: String? = request.getHeader(AUTHORIZATION_HEADER)

        if (authHeader.isNullOrEmpty() || !authHeader.startsWith(AUTH_TOKEN_PREFIX)) {
            return null
        }

        return authHeader.substring(7, authHeader.length)
    }

    fun validateTokenDateTime(token: String): Boolean {
        try {
            val claims = getClaims(token)

            if (claims.body.expiration.before(Date())) {
                return false
            }

            return true
        } catch (e: Exception) {
            when(e) {
                is IllegalArgumentException, is JwtException -> {
                    throw JwtAuthenticationException("JWT token is expired or invalid")
                }
                else -> throw e
            }
        }
    }

    fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.encodeToByteArray())).parseClaimsJws(token)
    }

    class JwtAuthenticationException(msg: String?) : AuthenticationException(msg)
}