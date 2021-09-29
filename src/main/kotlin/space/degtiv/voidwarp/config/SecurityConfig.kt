package space.degtiv.voidwarp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import space.degtiv.voidwarp.security.jwt.JwtTokenFilter
import space.degtiv.voidwarp.security.jwt.JwtTokenProvider
import space.degtiv.voidwarp.security.jwt.JwtUsernameAndPasswordAuthFilter
import space.degtiv.voidwarp.service.PlayerService

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val passwordEncoder: PasswordEncoder,
    val playerService: PlayerService,
    val jwtTokenFilter: JwtTokenFilter,
    val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter() {
    @Value("\${allowedFrontendOrigin}")
    private val allowedFrontendOrigin: String? = null

    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(JwtUsernameAndPasswordAuthFilter(authenticationManager(), jwtTokenProvider))
            .addFilterAfter(jwtTokenFilter, JwtUsernameAndPasswordAuthFilter::class.java)
            .authorizeRequests()
                .antMatchers("/api/v1/auth/logout").authenticated()
                .antMatchers("/", "index", "/css/*", "/js/*", "/api/v1/auth/**", "/login").permitAll()
            .anyRequest()
                .authenticated()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf("http://localhost:4200")
        configuration.allowedOrigins = mutableListOf("http://$allowedFrontendOrigin")
        configuration.allowedMethods = mutableListOf("*")
        configuration.allowedHeaders = mutableListOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(playerService)

        return provider
    }
}