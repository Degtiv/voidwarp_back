package space.degtiv.voidwarp.helper

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class ErrorMessage(val message: String,
                        val datetime: String = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                        val solution: String = "Please contact support degtiv@yandex.ru")