package icesi.edu.co.icesicare.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.model.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId

class EventViewModel : ViewModel() {

    val eventLV = MutableLiveData<Event>()
    val eventsLD = MutableLiveData<List<Event>>()
    val filteredEventsLD = MutableLiveData<List<Event>>()
    private var eventRepository: EventRepository = EventRepository()

    fun getEvent(eventId : String){
        viewModelScope.launch(Dispatchers.IO) {
            val event = eventRepository.getEvent(eventId)

            withContext(Dispatchers.Main){
                eventLV.value = event
            }
        }
    }

    fun getAllEvents(){
        viewModelScope.launch(Dispatchers.IO) {
            val events = eventRepository.getAllEvents()

            withContext(Dispatchers.Main){
                eventsLD.value = events
            }
        }
    }

    fun filterEventsByName(name:String){
        viewModelScope.launch (Dispatchers.IO){
            val filteredEvents = arrayListOf<Event>()
            eventsLD.value?.forEach{
                if(it.name.contains(name,ignoreCase = true)){
                    filteredEvents.add(it)
                }
            }
            withContext(Dispatchers.Main){
                filteredEventsLD.value = filteredEvents
            }
        }
    }

    fun clearFilter(){
        filteredEventsLD.value = arrayListOf<Event>()
    }

    /**
     * Filter events by isActive. An event is active if its date is after current date.
     */
    fun filterEventsByActive(isActive:Boolean){
        viewModelScope.launch (Dispatchers.IO){
            val filteredEvents = arrayListOf<Event>()

            if(isActive){
                eventsLD.value?.forEach{
                    if(it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isAfter(LocalDateTime.now())||
                        it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isEqual(LocalDateTime.now())){
                        filteredEvents.add(it)
                    }
                }
            }
            else{
                eventsLD.value?.forEach{
                    if(it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now())){
                        filteredEvents.add(it)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                filteredEventsLD.value = filteredEvents
            }
        }
    }
}