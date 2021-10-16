package space.degtiv.voidwarp.controller.api

import org.springframework.web.bind.annotation.*
import space.degtiv.voidwarp.helper.ErrorMessage
import space.degtiv.voidwarp.service.PlayerService

@RestController
@RequestMapping("api/v1/auth")
class AuthApiController(val playerService: PlayerService) {

    @PostMapping("registration")
    fun register(@RequestBody rqBody: RegisterRqBody): Any {
        return try {
            playerService.addAndEnablePlayer(rqBody.username, rqBody.password, rqBody.role)
        } catch (ex: Exception) {
            ErrorMessage(ex.message?:"Unable to create Player")
        }
    }

    @PostMapping("checkToken")
    fun checkToken() {
        
    }

    data class RegisterRqBody(val username: String, val password: String, val role: String?)
}