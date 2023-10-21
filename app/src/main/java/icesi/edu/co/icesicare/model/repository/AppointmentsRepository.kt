package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.entity.Student
import kotlinx.coroutines.tasks.await

class AppointmentsRepository {

    suspend fun getStudentOfAppointment(studentId : String) : Student{

        val docStudent = Firebase.firestore.collection("students")
            .document(studentId.replace("\"", "")).get().await()

        val student = docStudent.toObject(Student::class.java)

        student?.let {

            val url = Firebase.storage.reference
                .child("users")
                .child("profileImages")
                .child(it.profileImageId.toString()).downloadUrl.await()

            student.profileImageUrl = url.toString()
        }

        return student!!
    }
}