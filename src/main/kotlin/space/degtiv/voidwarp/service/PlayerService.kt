package space.degtiv.voidwarp.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import space.degtiv.voidwarp.domain.Player
import space.degtiv.voidwarp.repository.PlayerRepository
import space.degtiv.voidwarp.security.Role

@Service
class PlayerService(
    val playerRepository: PlayerRepository,
    val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return playerRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User with username = $username not found")
    }

    fun addAndEnablePlayer(username: String, password: String, role: String?): Player {
        val player = Player(username, passwordEncoder.encode(password))
        player.isActive = true
        if (role != null) {
            val foundRole = Role.values().firstOrNull { it.name.equals(role, true) }
            if (foundRole != null) {
                player.authorities = foundRole.getGrantedAuthorities()
            }
        }
        savePlayer(player)

        return player
    }

    fun getPlayerById(playerId: String): Player? {
        return playerRepository.findByUuid(playerId)
    }

    fun savePlayer(player: Player) {
        playerRepository.save(player)
    }

    fun getAllPlayers(): Iterable<Player?> {
        return playerRepository.findAll()
    }

}