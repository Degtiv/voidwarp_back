package space.degtiv.voidwarp.controller.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import space.degtiv.voidwarp.service.PlayerService

@RestController
@RequestMapping("api/v1/characters")
class CharacterController(val playerService: PlayerService) {
    @GetMapping("all")
    fun getCharacters(): Any {
        return playerService.getAllPlayers()
    }
}