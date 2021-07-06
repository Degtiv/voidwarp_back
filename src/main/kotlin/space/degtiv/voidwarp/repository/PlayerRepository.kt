package space.degtiv.voidwarp.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import space.degtiv.voidwarp.domain.Player

@Repository
interface PlayerRepository: CrudRepository<Player, String> {
    fun findByUuid(uuid: String): Player?
    fun findByUsername(username: String): Player?
}