package space.degtiv.voidwarp.controller.api

import org.springframework.web.bind.annotation.*
import space.degtiv.voidwarp.service.PlayerService

@RestController
@RequestMapping("api/v1/players")
class PlayerController(val playerService: PlayerService) {
    @GetMapping(path = ["{playerId}"])
    fun getPlayer(@PathVariable("playerId") playerId: String): Any? {
        return playerService.getPlayerById(playerId)
    }

    @PostMapping("all")
    fun getPlayers(): Any {
        return playerService.getAllPlayers()
    }
}