package space.degtiv.voidwarp.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*

@Document(collection = "players")
class Player(
    private var username: String,
    @JsonIgnore
    private var password: String
) : UserDetails, Serializable {
    @Id
    var uuid = UUID.randomUUID().toString()

    @JsonIgnore
    var authorities = mutableSetOf<SimpleGrantedAuthority>()
    var isActive = false

    override fun getAuthorities(): MutableCollection<out SimpleGrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isActive
    }

    override fun isAccountNonLocked(): Boolean {
        return isActive
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isActive
    }

    override fun isEnabled(): Boolean {
        return isActive
    }
}