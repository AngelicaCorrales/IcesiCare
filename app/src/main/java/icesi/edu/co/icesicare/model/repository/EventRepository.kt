package icesi.edu.co.icesicare.model.repository

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.entity.EventFirebase
import icesi.edu.co.icesicare.model.entity.EventsHelper
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.collections.arrayListOf

class EventRepository {

    suspend fun getEvent(eventId : String) : Event? {

        val docEvent = Firebase.firestore.collection("events")
            .document(eventId).get().await()


        val eventFirebase = docEvent.toObject(EventFirebase::class.java)

        val event= eventFirebase?.let { EventsHelper.eventFirebaseToEvent(it) }

        event?.let {

            if (it.imageId != ""){
                val url = Firebase.storage.reference
                    .child("events")
                    .child(it.imageId.toString()).downloadUrl.await()

                event.imageURL = url.toString()
            }
        }

        return event
    }

    suspend fun getAllEvents():MutableList<Event>{
        val result = Firebase.firestore.collection("events").get().await()

        val events = arrayListOf<Event>()

        result.documents.forEach{document ->
            val eventFirebase = document.toObject(EventFirebase::class.java)

            val event= eventFirebase?.let { EventsHelper.eventFirebaseToEvent(it) }
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

    suspend fun deleteEvent(eventId: String) {
        val docEvent = Firebase.firestore.collection("events")
            .document(eventId).get().await()

        val eventFirebase = docEvent.toObject(EventFirebase::class.java)

        val event= eventFirebase?.let { EventsHelper.eventFirebaseToEvent(it) }


        event?.let {
            if(it.imageId != ""){
                Firebase.storage.reference
                    .child("events")
                    .child(it.imageId).delete().await()
            }
        }

        docEvent.reference.delete()
    }

    /**
     * Update an event in firebase.
     * This function assumes that event.imageURL references a local image file
     * and that event.imageID attribute is unchanged.
     */
    suspend fun updateEvent(event:Event, isImageUpdated:Boolean){
        Firebase.firestore.collection("events")
            .document(event.id).get().await() //Checks event exists

        if(isImageUpdated && event.imageId != ""){//Deletes previous image
            Firebase.storage.reference
                .child("events")
                .child(event.imageId).delete().await()
            event.imageId = ""
        }

        if(isImageUpdated && event.imageURL != ""){//Upload new image if it has one
            val uuid = UUID.randomUUID().toString()

            Firebase.storage.reference
                .child("events")
                .child(uuid)
                .putFile(Uri.parse(event.imageURL)).await()
            event.imageId = uuid
        }

        val eventF = EventsHelper.eventToEventFirebase(event)
        Firebase.firestore.collection("events")
            .document(eventF.id).set(eventF).await()

    }

    suspend fun createEvent(event:Event):String{
        if(event.id != ""){
            throw Exception("New events must not have an id")
        }

        if(event.imageURL != ""){
            val imageUuid = UUID.randomUUID().toString()

            Firebase.storage.reference
                .child("events")
                .child(imageUuid)
                .putFile(Uri.parse(event.imageURL)).await()
            event.imageId = imageUuid
        }

        val uuid = UUID.randomUUID().toString()
        event.id = uuid

        val eventF = EventsHelper.eventToEventFirebase(event)
        Firebase.firestore
            .collection("events")
            .document(uuid).set(eventF).await()

        return event.id
    }
}
