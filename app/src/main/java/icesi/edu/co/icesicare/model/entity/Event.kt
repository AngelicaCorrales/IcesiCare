package icesi.edu.co.icesicare.model.entity


import java.time.LocalDateTime
data class Event (
    var category : String = "",
    var date : LocalDateTime= LocalDateTime.now(),
    var id : String = "",
    var imageId: String = "",
    var imageURL : String = "",
    var name : String = "",
    var space : String = ""
)


data class EventFirebase(
    var category: String = "",
    var date : LocalDateTime= LocalDateTime.now(),
    var id : String = "",
    var imageId : String = "",
    var name : String = "",
    var space : String = ""
)

class EventsHelper{
    companion object{
        fun eventToEventFirebase(event: Event): EventFirebase {
            return EventFirebase(
                event.category,
                event.date,
                event.id,
                event.imageId,
                event.name,
                event.space
            )
        }
    }
}

