package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Appointments
import icesi.edu.co.icesicare.model.repository.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppointmentsListViewModel : ViewModel() {
    val appointmentsList = ArrayList<Appointments>()
    val appointmentsListLiveData = MutableLiveData<ArrayList<Appointments>>()
    lateinit var firebaseRepository: FirebaseRepository

    fun downloadAppointments() {
        appointmentsList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val appointments = firebaseRepository.getAppointmentsForStudent("UjZ9bvrXxCexOXvFV2nF")
                appointmentsList.addAll(appointments)

                withContext(Dispatchers.Main) {
                    appointmentsListLiveData.value = appointmentsList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
