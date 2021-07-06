package space.degtiv.voidwarp.security

enum class Authority(val authority: String) {
    PLAYER_READ("PLAYER:READ"),
    PLAYER_WRITE("PLAYER:WRITE"),
    CHARACTER_READ("CHARACTER:READ"),
    CHARACTER_WRITE("CHARACTER:WRITE")
}