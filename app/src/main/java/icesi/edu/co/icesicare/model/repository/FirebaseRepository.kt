package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.AppointmentFirebase
import icesi.edu.co.icesicare.model.entity.AppointmentHelper
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.entity.EventFirebase
import icesi.edu.co.icesicare.model.entity.EventsHelper
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date


class FirebaseRepository {

    suspend fun getAppointmentsForStudent(studentId: String, month : Int): ArrayList<Appointment> {
        val appointmentsList = ArrayList<Appointment>()
        try {
            val appointmentIdsQuery = Firebase.firestore.collection("students")
                .document(studentId)
                .collection("appointment")
                .get()
                .await()
            val appointmentIds = appointmentIdsQuery.documents.map { it.id }
            appointmentIds.forEach {
                val appointmentsQuery = Firebase.firestore.collection("appointments")
                    .document(it)
                    .get()
                    .await()
                val appointment = appointmentsQuery.toObject(AppointmentFirebase::class.java)
                val event= appointment?.let { AppointmentHelper.appointmentFirebaseToAppointment(it) }
                event?.let {
                        app ->

                    if (getMonthFromDate(event.date,month)) {
                        appointmentsList.add(app)
                    }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return appointmentsList

    }

    fun getMonthFromDate2(date: Timestamp, valueMonth: Int): Boolean {
        return false;
    }


    fun getMonthFromDate(date: LocalDateTime, valueMonth : Int): Boolean {
        var  isValid :Boolean = false
        if(date.monthValue.toString().equals(valueMonth.toString())){
            isValid = true
        }
        return isValid
    }

    suspend fun gePsychologistAppointment(psychologistId: String): Psychologist {
        val appointmentsQuery = Firebase.firestore.collection("psychologists")
            .document(psychologistId)
            .get()
            .await()
        val appointment = appointmentsQuery.toObject(Psychologist::class.java)
        return appointment!!
    }

    suspend fun getEvents(month : Int): ArrayList<Event> {
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
                event?.let { app ->
                    if (getMonthFromDate(event.date,month)) {
                        events.add(app)
                    }
                }
            }
        }
        return events

    }

}
