package space.degtiv.voidwarp.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "players")
class Player(
    @Column(length = 100, nullable = false, updatable = true)
    private var username: String,
    @JsonIgnore
    @Column(length = 100, nullable = false, updatable = true)
    private var password: String
) : UserDetails, Serializable {
    @Id
    @Column(length = 100, nullable = false, updatable = false)
    val uuid = UUID.randomUUID().toString()

    @CollectionTable(name = "authorities", joinColumns = [JoinColumn(name = "player_id")])
    @ElementCollection(targetClass = SimpleGrantedAuthority::class, fetch = FetchType.EAGER)
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