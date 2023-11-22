package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel : ViewModel() {

    val eventLV = MutableLiveData<Event>()
    private var eventRepository: EventRepository = EventRepository()

    fun getEvent(eventId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val event = eventRepository.getEvent(eventId)

            withContext(Dispatchers.Main){
                eventLV.value = event
            }
        }
    }
}