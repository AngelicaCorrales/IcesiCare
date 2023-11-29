package icesi.edu.co.icesicare.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CalendarUtils {
    var selectedDate: LocalDate? = null


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
}