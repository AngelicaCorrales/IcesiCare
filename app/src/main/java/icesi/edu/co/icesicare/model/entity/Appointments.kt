package icesi.edu.co.icesicare.model.entity

import java.util.Date

data class Appointments (
     var date: Date,
     var id:String,
     var isCanceled: Boolean,
     var motive:String,
     var psychologistId: String,
     var studentId:String

     )