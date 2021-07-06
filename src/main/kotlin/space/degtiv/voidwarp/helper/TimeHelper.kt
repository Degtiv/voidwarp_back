package space.degtiv.voidwarp.helper

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class TimeHelper {
    companion object {
        fun convertLocalDateTimeToDate(localDateTime: LocalDateTime): Date {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        }

        fun dateToLocalDateTime(date: Date): LocalDateTime {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
}