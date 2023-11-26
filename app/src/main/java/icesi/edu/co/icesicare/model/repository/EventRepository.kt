package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.tasks.await
import kotlin.collections.arrayListOf

class EventRepository {

    suspend fun getEvent(eventId : String) : Event {

        val docEvent = Firebase.firestore.collection("events")
            .document(eventId).get().await()

        val event = docEvent.toObject(Event::class.java)

        event?.let {

            if (it.imageId != ""){
                val url = Firebase.storage.reference
                    .child("events")
                    .child(it.imageId.toString()).downloadUrl.await()

                event.imageURL = url.toString()
            }
        }

        return event!!
    }

    suspend fun getAllEvents():List<Event>{
        val result = Firebase.firestore.collection("events").get().await()

        val events = arrayListOf<Event>()

        result.documents.forEach{document ->
            val event = document.toObject(Event::class.java)

            event?.let {
                if(it.imageId != "" ){
                    val url= Firebase.storage.reference
                        .child("events")
                        .child(it.imageId).downloadUrl.await()
                    it.imageURL = url.toString()
                }
                events.add(it)
            }
        }

        return events
    }
}
