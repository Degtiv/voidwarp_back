package space.degtiv.voidwarp.controller.api

import org.springframework.web.bind.annotation.*
import space.degtiv.voidwarp.service.PlayerService

@RestController
@RequestMapping("api/v1/auth")
class AuthApiController(val playerService: PlayerService) {
    @PostMapping("registration")
    fun register(@RequestBody rqBody: RegisterRqBody): Any {
        return playerService.addAndEnablePlayer(rqBody.username, rqBody.password, rqBody.role)
    }

    data class RegisterRqBody(val username: String, val password: String, val role: String?)
}