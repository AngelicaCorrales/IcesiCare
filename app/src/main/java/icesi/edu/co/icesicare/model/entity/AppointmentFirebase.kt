package icesi.edu.co.icesicare.model.entity

import java.sql.Timestamp

data class AppointmentFirebase (

    var id : String = "",
    var date : Timestamp = Timestamp(System.currentTimeMillis()),
    var canceled : Boolean = false,
    var approved: Boolean = false,
    var motive : String = "",
    var psychologistId : String = "",
    var studentId : String = ""
)