package icesi.edu.co.icesicare.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.DayOfWeek
import java.time.LocalDate

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime

import java.time.format.DateTimeFormatter
import java.util.Date

object CalendarUtils {

     val selectedDate = MutableLiveData<LocalDate?>()


    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }


    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = mondayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    private fun mondayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.MONDAY) return current
            current = current.minusDays(1)
        }
        return null
    }

    val LocalDateTime.toDate: Date
        get() = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())

    val Date.toLocalDateTime: LocalDateTime
        get() = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
}