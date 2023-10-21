package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentViewModel : ViewModel() {

    val appointmentLV = MutableLiveData<Appointment>()
    val psychologistLV = MutableLiveData<Psychologist>()
    val appointmentsRepository : AppointmentsRepository = AppointmentsRepository()

    fun getStudentAppointment(appointmentId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val appointment = appointmentsRepository.getAppointment(appointmentId)

            withContext(Dispatchers.Main){
                appointmentLV.value = appointment
            }
            getPsychologistOfAppointment(appointment.psychologistId.toString())
        }
    }

    fun getPsychologistOfAppointment(psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val psychologist = appointmentsRepository.getPsychologistOfAppointment(psychologistId)

            withContext(Dispatchers.Main){
                psychologistLV.value = psychologist
            }
        }
    }
}