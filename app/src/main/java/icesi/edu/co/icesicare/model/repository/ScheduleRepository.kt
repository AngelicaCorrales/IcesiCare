package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

}