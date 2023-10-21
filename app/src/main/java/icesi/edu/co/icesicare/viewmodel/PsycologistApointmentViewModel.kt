package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PsycologistApointmentViewModel : ViewModel(){

    val appointmentLV = MutableLiveData<Appointment>()
    val studentLV = MutableLiveData<Student>()
    var appointmentsRepository: AppointmentsRepository = AppointmentsRepository()

    fun getPsychologistAppointment(appointmentId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val appointment = appointmentsRepository.getAppointment(appointmentId)

            withContext(Dispatchers.Main){
                appointmentLV.value = appointment
            }
            getStudentOfAppointment(appointment.studentId.toString())
        }
    }

    fun getStudentOfAppointment(studentId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val student = appointmentsRepository.getStudentOfAppointment(studentId)

            withContext(Dispatchers.Main){
                studentLV.value = student
            }
        }
    }
}