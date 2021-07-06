package space.degtiv.voidwarp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class VoidwarpApplication()

fun main(args: Array<String>) {
    runApplication<VoidwarpApplication>(*args)
}
