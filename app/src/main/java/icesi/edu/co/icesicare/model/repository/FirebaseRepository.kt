package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointments
import kotlinx.coroutines.tasks.await


interface FirebaseRepository {
    suspend fun getAppointmentsForStudent(studentId: String): List<Appointments> {
        val appointmentsList = mutableListOf<Appointments>()

        try {
            val appointmentIdsQuery = Firebase.firestore.collection("students")
                .document(studentId)
                .collection("appointments")
                .get()
                .await()

            val appointmentIds = appointmentIdsQuery.documents.map { it.id }

            val appointmentsQuery = Firebase.firestore.collection("appointments")
                .whereIn(FieldPath.documentId(), appointmentIds)
                .get()
                .await()

            for (document in appointmentsQuery.documents) {
                val appointment = document.toObject(Appointments::class.java)
                if (appointment != null) {
                    appointmentsList.add(appointment)
                }
            }
        } catch (e: Exception) {
        }
        return appointmentsList
    }
}
