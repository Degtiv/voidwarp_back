package space.degtiv.voidwarp.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import space.degtiv.voidwarp.security.Authority.*

enum class Role(val authorities: MutableSet<Authority>) {
    PLAYER(mutableSetOf()),
    ADMIN(mutableSetOf(CHARACTER_READ, PLAYER_READ, PLAYER_WRITE));

    fun getGrantedAuthorities(): MutableSet<SimpleGrantedAuthority> {
        val authorities = authorities.map { SimpleGrantedAuthority(it.authority) }.toMutableSet()
        authorities.add(SimpleGrantedAuthority("ROLE_${name}"))
        return authorities
    }
}