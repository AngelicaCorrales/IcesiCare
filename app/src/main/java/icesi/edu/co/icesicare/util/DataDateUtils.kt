package icesi.edu.co.icesicare.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DataDateUtils {

    fun formatHour(date : LocalDateTime) : String{
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = date.format(formatterTime)

        return formattedTime
    }


    fun formatDay(date : LocalDateTime) : String{
        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = date.format(formatterDate)
        return formattedDate
    }
}