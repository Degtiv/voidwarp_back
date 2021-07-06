package space.degtiv.voidwarp.security.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = jwtTokenProvider.resolveToken(request)
            if (token != null && jwtTokenProvider.validateTokenDateTime(token)) {
                val claimsJws = jwtTokenProvider.getClaims(token)
                val body = claimsJws.body
                val authorities = (body.get("authorities") as MutableList<MutableMap<String, String>>)
                    .map { SimpleGrantedAuthority(it.get("authority")) }
                val authentication = UsernamePasswordAuthenticationToken(body.subject, null, authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
            filterChain.doFilter(request, response)
        } finally {
            response.contentType = "application/json";
        }
    }
}