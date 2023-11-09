package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date


class FirebaseRepository {

    suspend fun getAppointmentsForStudent(studentId: String, month : Int): ArrayList<Appointments> {
        val appointmentsList = ArrayList<Appointments>()

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
                val appointment = appointmentsQuery.toObject(Appointments::class.java)
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
    fun getMonthFromDate(date: Date, valueMonth : Int): Boolean {
        var  isValid :Boolean = false
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH) + 1
        if(month.toString().equals(valueMonth.toString())){
            isValid = true
        }
        return isValid
    }
}
