package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PsycologistApointmentViewModel : ViewModel(){

    val appointmentLV = MutableLiveData<Appointment>()
    val studentLV = MutableLiveData<Student>()

    fun getPsychologistAppointment(appointmentId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val document = Firebase.firestore.collection("appointments")
                .document(appointmentId).get().await()

            val appointment = document.toObject(Appointment::class.java)

            appointment?.let {

                withContext(Dispatchers.Main){
                    appointmentLV.value = it
                }
                getStudentOfAppointment(it.studentId.toString())
            }
        }
    }

    fun getStudentOfAppointment(studentId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val docStudent = Firebase.firestore.collection("students")
                .document(studentId.replace("\"", "")).get().await()

            val student = docStudent.toObject(Student::class.java)

            student?.let {
                withContext(Dispatchers.Main){
                    studentLV.value = it
                }
            }
        }
    }
}