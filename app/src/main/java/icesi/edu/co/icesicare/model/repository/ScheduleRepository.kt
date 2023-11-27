package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Schedule
import kotlinx.coroutines.tasks.await
object ScheduleRepository {

    private val firebaseFirestore = Firebase.firestore

    suspend fun createEmptyScheduleForPsychologist(psychologistId: String): String {
        val newScheduleRef = firebaseFirestore.collection("schedule").document()
        val newSchedule = Schedule(psychologistId = psychologistId, scheduleId = newScheduleRef.id)
        newScheduleRef.set(newSchedule).await()
        return newScheduleRef.id
    }

    suspend fun getSchedule(scheduleId: String): Schedule? {
        try {
            val scheduleSnapshot = Firebase.firestore
                .collection("schedules")
                .document(scheduleId)
                .get()
                .await()
            return scheduleSnapshot.toObject(Schedule::class.java)
        } catch (e: Exception) {
            Log.e("ScheduleRepository", "Error fetching Schedule", e)
            return null
        }


    }
}