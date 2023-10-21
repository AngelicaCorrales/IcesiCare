package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.tasks.await

class AppointmentsRepository {

    suspend fun getAppointment(appointmentId : String) : Appointment {
        val document = Firebase.firestore.collection("appointments")
            .document(appointmentId).get().await()

        val appointment = document.toObject(Appointment::class.java)

        return appointment!!
    }

    suspend fun getPsychologistOfAppointment(psychologistId : String) : Psychologist{
        val docPsy = Firebase.firestore.collection("psychologists")
            .document(psychologistId.replace("\"", "")).get().await()

        val psychologist = docPsy.toObject(Psychologist::class.java)

        psychologist?.let {

            val url = Firebase.storage.reference
                .child("users")
                .child("profileImages")
                .child(it.profileImageId.toString()).downloadUrl.await()

            psychologist.profileImageURL = url.toString()
        }

        return psychologist!!
    }
}