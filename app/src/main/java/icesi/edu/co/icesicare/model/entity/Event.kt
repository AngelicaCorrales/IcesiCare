package icesi.edu.co.icesicare.model.entity

import java.util.Date

data class Event(

    var category: String = "",
    var date : Date = Date(),
    var id : String = "",
    var imageId : String = "",
    var imageURL : String = "",
    var name : String = "",
    var space : String = ""
)