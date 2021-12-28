package space.degtiv.voidwarp.controller.api

import org.springframework.web.bind.annotation.*
import space.degtiv.voidwarp.service.PlayerService
import kotlin.streams.toList

@RestController
@RequestMapping("api/v1/players")
class PlayerController(val playerService: PlayerService) {
    @GetMapping(path = ["{uuid}"])
    fun getPlayer(@PathVariable("uuid") uuid: String): Any? {
        return playerService.getPlayerById(uuid)
    }

    @PostMapping("all")
    fun getPlayers(): Any {
        return playerService.getAllPlayers().toList().stream()
            .map { player -> player?.let { PlayerResponseBody(it.username, player.uuid, player.isActive) } }.toList()
    }

    @PostMapping("{uuid}/delete")
    fun deletePlayer(@PathVariable("uuid") uuid: String): Any? {
        try {
            playerService.deletePlayerById(uuid)
        } catch (argumentException: IllegalArgumentException) {
            return argumentException.message
        }
        return DeletedPlayerResponseBody(uuid = uuid)
    }

    data class PlayerResponseBody(val username: String, val uuid: String, val isActive: Boolean)
    data class DeletedPlayerResponseBody(val uuid: String)
}