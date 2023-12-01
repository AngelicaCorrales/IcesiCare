package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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
                val appointment = appointmentsQuery.toObject(Appointment::class.java)
                appointment?.let { app ->
                    if (getMonthFromDate(appointment.date,month)) {
                        appointmentsList.add(app)
                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return appointmentsList

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
        val appointmentsQuery = Firebase.firestore.collection("events")
            .get()
            .await()

        val appointmentsList = ArrayList<Event>()

        appointmentsQuery.documents.forEach { document ->
            val appointment = document.toObject(Event::class.java)
            appointment?.let { app ->
                Log.e(">>>",appointment.date.toString() + "   en repositorio" )
                if (getMonthFromDate(appointment.date,month)) {
                    appointmentsList.add(app)
                }
            }
        }

        return appointmentsList
    }

}
