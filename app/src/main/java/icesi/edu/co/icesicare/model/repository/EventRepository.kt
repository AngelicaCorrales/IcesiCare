package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Event
import kotlinx.coroutines.tasks.await

class EventRepository {

    suspend fun getEvent(eventId : String) : Event {

        val docEvent = Firebase.firestore.collection("events")
            .document(eventId).get().await()

        val event = docEvent.toObject(Event::class.java)

        return event!!
    }
}