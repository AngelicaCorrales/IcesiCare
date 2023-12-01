package icesi.edu.co.icesicare.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.AppointmentFirebase
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Student
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object AppointmentsRepository {

    private val appts = arrayListOf<Appointment>()
    val apptsLiveData = MutableLiveData<ArrayList<Appointment>>(appts)

    private val studRelatedAppt = HashMap<String,Student>()
    val studRelatedApptLiveData = MutableLiveData<HashMap<String,Student>> (studRelatedAppt)

    //This will overwrite psychAppts data
    suspend fun fetchAppointmentsForPsychologist(psychId: String, isAccepted:Boolean, isCanceled:Boolean){
        try {
            appts.clear()
            studRelatedAppt.clear()
            val result = Firebase.firestore
                .collection("appointments")
                .whereEqualTo("psychologistId",psychId)
                .whereEqualTo("isAccepted",isAccepted)
                .whereEqualTo("isCanceled",isCanceled)
                .get().await()
            result.documents.forEach {document ->
                val appt = document.toObject(Appointment::class.java)

                appt?.let {
                    appts.add(it)
                    fetchStudentForAppt(appt.studentId)
                }
            }

            apptsLiveData.postValue(appts)
            studRelatedApptLiveData.postValue(studRelatedAppt)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchStudentForAppt(studentId:String){
        val document = Firebase.firestore
            .collection("students")
            .document(studentId)
            .get().await()

        val student = document.toObject(Student::class.java)

        student?.let {
            if(it.profileImageId != null && it.profileImageId != "" ){
                val url= Firebase.storage.reference
                    .child("users")
                    .child("profileImages")
                    .child(it.profileImageId!!).downloadUrl.await()
                it.profileImageURL = url.toString()
            }
            studRelatedAppt[studentId] = it
        }

    }

    suspend fun updateAppointmentStatus(apptId: String, isAccepted:Boolean, isCanceled:Boolean){
        try {
            Firebase.firestore
                .collection("appointments")
                .document(apptId).update("isAccepted",isAccepted).await()

            Firebase.firestore
                .collection("appointments")
                .document(apptId).update("isCanceled",isCanceled).await()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getAppointment(appointmentId : String) : Appointment {
        val document = Firebase.firestore.collection("appointments")
            .document(appointmentId).get().await()

        val appointment = document.toObject(Appointment::class.java)

        return appointment!!
    }

    suspend fun getPsychologistOfAppointment(psychologistId : String) : Psychologist {
        val docPsy = Firebase.firestore.collection("psychologists")
            .document(psychologistId.replace("\"", "")).get().await()

        val psychologist = docPsy.toObject(Psychologist::class.java)

        psychologist?.let {

            if (it.profileImageId != ""){
                val url = Firebase.storage.reference
                    .child("users")
                    .child("profileImages")
                    .child(it.profileImageId.toString()).downloadUrl.await()

                psychologist.profileImageURL = url.toString()
            }
        }

        return psychologist!!
    }

    suspend fun saveAppointment(appointment: Appointment){
        appointment.studentId=Firebase.auth.currentUser!!.uid

        // LocalDateTime to epoch milliseconds
        val milliseconds = appointment.date.atZone(ZoneId.of("America/Bogota"))?.toInstant()?.toEpochMilli()

        val appoint= AppointmentFirebase(appointment.id,
            Timestamp(milliseconds!!),appointment.canceled,appointment.approved,appointment.motive,appointment.psychologistId,appointment.studentId)
       Firebase.firestore
           .collection("appointments")
           .document(appointment.id)
           .set(appoint)
           .await()
    }
}