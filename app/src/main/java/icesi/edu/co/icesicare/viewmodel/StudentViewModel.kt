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

    fun getStudentAppointment(appointmentId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val appointment = AppointmentsRepository.getAppointment(appointmentId)

            withContext(Dispatchers.Main){
                appointmentLV.value = appointment
            }
            getPsychologistOfAppointment(appointment.psychologistId.toString())
        }
    }

    private fun getPsychologistOfAppointment(psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {

            val psychologist = AppointmentsRepository.getPsychologistOfAppointment(psychologistId)

            withContext(Dispatchers.Main){
                psychologistLV.value = psychologist
            }
        }
    }
}