package icesi.edu.co.icesicare.model.entity

import java.util.Date

data class Message(

    var authorId : String = "",
    var date : Date = Date(),
    var id : String = "",
    var message : String = ""
)
