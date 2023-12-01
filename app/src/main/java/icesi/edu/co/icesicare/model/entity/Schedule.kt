package icesi.edu.co.icesicare.model.entity

data class Schedule (
    var psychologistId : String = "",
    var scheduleId : String = "",
)

data class Schedules(
    var id: String = "",
    var day: String = "",
    var startHour: Double? = 0.0,
    var endHour: Double? = 0.0
)