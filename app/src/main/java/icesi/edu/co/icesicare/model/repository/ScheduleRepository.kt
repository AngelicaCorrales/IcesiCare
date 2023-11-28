package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Schedule
import icesi.edu.co.icesicare.model.entity.Schedules
import kotlinx.coroutines.tasks.await
object ScheduleRepository {

    private val firebaseFirestore = Firebase.firestore

    suspend fun createEmptyScheduleForPsychologist(psychologistId: String): String {
        val newScheduleRef = firebaseFirestore.collection("schedule").document()
        val newSchedule = Schedule(psychologistId = psychologistId, scheduleId = newScheduleRef.id)
        newScheduleRef.set(newSchedule).await()

        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        days.forEach { day ->
            val daySchedule = Schedules(day = day, startHour = "", endHour = "")
            newScheduleRef.collection("schedules").document(day).set(daySchedule).await()
        }

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

    suspend fun getScheduleForDay(scheduleId: String, day: String): Schedules? {
        try {
            val dayScheduleSnapshot = firebaseFirestore
                .collection("schedule")
                .document(scheduleId)
                .collection("schedules")
                .document(day)
                .get()
                .await()
            return dayScheduleSnapshot.toObject(Schedules::class.java)
        } catch (e: Exception) {
            Log.e("ScheduleRepository", "Error fetching Schedule for a day", e)
            return null
        }
    }

}