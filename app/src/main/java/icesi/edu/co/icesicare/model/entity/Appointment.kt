package icesi.edu.co.icesicare.model.entity

import java.util.Date

data class Appointment (

    var id : String = "",
    var date : Date = Date(),
    var isCanceled : Boolean = false,
    var isApproved: Boolean = false,
    var motive : String = "",
    var psychologistId : String = "",
    var studentId : String = ""
)