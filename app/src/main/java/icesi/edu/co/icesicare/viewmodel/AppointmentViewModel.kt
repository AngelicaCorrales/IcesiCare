package icesi.edu.co.icesicare.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Psychologist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppointmentViewModel : ViewModel() {

    val appointmentLV = MutableLiveData<Appointment>()
    val psychologistLV = MutableLiveData<Psychologist>()

    fun getStudentAppointment(appointmentId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val document = Firebase.firestore.collection("appointments")
                .document(appointmentId).get().await()

            val appointment = document.toObject(Appointment::class.java)

            appointment?.let {

                withContext(Dispatchers.Main){
                    appointmentLV.value = it
                }
                getPsychologistOfAppointment(it.psychologistId.toString())
            }
        }
    }

    fun getPsychologistOfAppointment(psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val docPsy = Firebase.firestore.collection("psychologists")
                .document(psychologistId.replace("\"", "")).get().await()

            val psychologist = docPsy.toObject(Psychologist::class.java)

            psychologist?.let {
                withContext(Dispatchers.Main){
                    psychologistLV.value = it
                }
            }
        }
    }
}