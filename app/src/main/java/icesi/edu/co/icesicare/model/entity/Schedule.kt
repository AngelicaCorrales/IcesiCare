package icesi.edu.co.icesicare.model.entity

data class Schedule (
    var psychologistId : String = "",
    var scheduleId : String = "",
    var schedules: Map<String, Schedule> = emptyMap()
)

data class Schedules(
    var id: String = "",
    var day: String = "",
    var startHour: String = "",
    var endHour: String = ""
)