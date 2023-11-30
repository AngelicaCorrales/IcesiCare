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
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.entity.Psychologist
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

    val appointmentsListLiveData = MutableLiveData<ArrayList<AppointmentData>>()
    val eventsListLiveData = MutableLiveData<ArrayList<Event>>()
    private var currentMonth = 1;
    private var typeConsutl = 1;

    fun downloadAppointments(studentId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val appointmentsList = firebaseRepository.getAppointmentsForStudent(studentId, currentMonth)
            val psychologist = ArrayList<Psychologist>()
            val appointmentDataList = ArrayList<AppointmentData>()
           if(appointmentsList.size>0){
               appointmentsList.forEach { appointment ->
                   val result = firebaseRepository.gePsychologistAppointment(appointment.psychologistId)

                   result.let {
                       psychologist.add(result)
                   }
               }
               for (i in appointmentsList.indices) {
                   val appointmentData = AppointmentData(appointmentsList.get(i).date, psychologist.get(i).name, psychologist.get(1).id )
                   appointmentDataList.add(appointmentData)
               }
           }

            withContext(Dispatchers.Main) {
                appointmentsListLiveData.value = appointmentDataList
            }
        }

    }


    @SuppressLint("SuspiciousIndentation")
    fun downloadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            val eventList = firebaseRepository.getEvents(currentMonth)
            withContext(Dispatchers.Main) {
                Log.e(">>>" ,eventList.size.toString() +" tamaño en events en viewModel 69" )

                eventsListLiveData.value = eventList
            }
        }

    }
    fun setMonth(month:Int){
        this.currentMonth = month
    }
    fun setType(type:Int){

        this.typeConsutl = type

    }
    fun getType(): Int {
        return this.typeConsutl
    }


}
data class AppointmentData(
    val date: Date= Date(),
    val PsychologistName: String,
    val psychologistId : String
)


