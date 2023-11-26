package icesi.edu.co.icesicare.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Appointments
import icesi.edu.co.icesicare.model.repository.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class AppointmentsListViewModel : ViewModel() {

//solo el viewModel puede guardar datos.
    private var firebaseRepository = FirebaseRepository()

    val appointmentsListLiveData = MutableLiveData<ArrayList<Appointment>>()
    private var currentMonth = 1;
    private var studentId = "UjZ9bvrXxCexOXvFV2nF"

    @SuppressLint("SuspiciousIndentation")
    fun downloadAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            val appointmentsList = firebaseRepository.getAppointmentsForStudent(studentId, currentMonth)
            withContext(Dispatchers.Main) {
                appointmentsListLiveData.value = appointmentsList
            }
        }

    }

    fun setMonth(month:Int){
        this.currentMonth = month
    }



}
data class AppointmentData(
    val date: Date= Date(),
    val PsychologistName: String
)


