package space.degtiv.voidwarp.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtUsernameAndPasswordAuthFilter(
    private val authManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val authRequest = ObjectMapper().readValue(request.inputStream, UsernameAndPasswordAuthRequest::class.java)
        val authentication = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        return authManager.authenticate(authentication)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val token = jwtTokenProvider.createToken(authResult.name, authResult.authorities)
        response.writer.write("{\"$AUTHORIZATION_HEADER\": \"$AUTH_TOKEN_PREFIX$token\"}")
        response.writer.flush()
    }

    class UsernameAndPasswordAuthRequest {
        val username: String? = null
        val password: String? = null
    }
}