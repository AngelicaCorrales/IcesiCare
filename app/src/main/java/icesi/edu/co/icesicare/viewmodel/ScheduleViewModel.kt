package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Schedules
import icesi.edu.co.icesicare.model.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel:ViewModel() {
    val scheduleLV= MutableLiveData<Schedules?>()

    fun getScheduleForDay(scheduleId: String, day: String){

        viewModelScope.launch (Dispatchers.IO){
            val daySchedule = ScheduleRepository.getScheduleForDay(scheduleId, day)
            withContext(Dispatchers.Main){
                scheduleLV.value=daySchedule
            }

        }
    }

}