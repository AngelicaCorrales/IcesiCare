package icesi.edu.co.icesicare.model.entity

import java.time.LocalDateTime
import java.util.Date

data class Event (
    var category : String = "",
    var date : LocalDateTime= LocalDateTime.now(),
    var id : String = "",
    var imageId: String = "",
    var name : String = "",
    var space : String = ""
)