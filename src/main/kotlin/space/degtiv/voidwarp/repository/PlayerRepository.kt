package space.degtiv.voidwarp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import space.degtiv.voidwarp.domain.Player

@Repository
interface PlayerRepository: MongoRepository<Player, String> {
    fun findByUuid(uuid: String): Player?
    fun findByUsername(username: String): Player?
}