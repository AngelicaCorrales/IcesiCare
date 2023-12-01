package icesi.edu.co.icesicare.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.ErrorMessage
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

class AppointmentViewModel : ViewModel() {
    val errorLV = MutableLiveData<ErrorMessage>()
    val appointmentLV=MutableLiveData<Appointment>()


    fun saveAppointment( date : LocalDate,hour: Double, motive : String, psychologistId : String){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uuid = UUID.randomUUID().toString()
                val hours = hour.toInt()
                val minutes = ((hour - hours) * 60).toInt()


                val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = date.format(formatterDate)
                val localTime = LocalTime.of(hours, minutes)
                val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
                val formattedTime = localTime.format(formatterTime)



                val fullDate= "$formattedDate $formattedTime"
                val pattern = "yyyy-MM-dd HH:mm"
                val formatter = DateTimeFormatter.ofPattern(pattern)
                val dateWithHour = LocalDateTime.parse(fullDate, formatter)

                val appointment= Appointment(uuid, dateWithHour,false,false,motive,psychologistId,"")

                AppointmentsRepository.saveAppointment(appointment)

                withContext(Dispatchers.Main){
                    appointmentLV.value = appointment
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main){
                    errorLV.value = ErrorMessage("Ha ocurrido un error")
                }
            }

        }

    }
}