package icesi.edu.co.icesicare.model.entity


import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

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
    var date: Date =Date(),
    var id: String = "",
    var imageId: String = "",
    var name: String = "",
    var space: String = ""
)

class EventsHelper{
    companion object{
        fun eventToEventFirebase(event: Event): EventFirebase {
            val milliseconds = event.date.atZone(ZoneId.of("America/Bogota"))?.toInstant()?.toEpochMilli()

            return EventFirebase(
                event.category,
                Timestamp(milliseconds!!),
                event.id,
                event.imageId,
                event.name,
                event.space
            )
        }

        fun eventFirebaseToEvent(eventFirebase: EventFirebase): Event {
            val milliseconds = eventFirebase.date.time
            val dateF = LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.of("America/Bogota"))

            return Event(
                eventFirebase.category,
                dateF,
                eventFirebase.id,
                eventFirebase.imageId,
                "",
                eventFirebase.name,
                eventFirebase.space
            )
        }


    }
}

