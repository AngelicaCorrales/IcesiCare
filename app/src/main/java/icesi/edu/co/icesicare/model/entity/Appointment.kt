package icesi.edu.co.icesicare.model.entity

import java.time.LocalDateTime

data class Appointment (

    var id : String = "",
    var date : LocalDateTime = LocalDateTime.now(),
    var canceled : Boolean = false,
    var approved: Boolean = false,
    var motive : String = "",
    var psychologistId : String = "",
    var studentId : String = ""
)