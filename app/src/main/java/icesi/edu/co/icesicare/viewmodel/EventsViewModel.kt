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
    val eventsLD = MutableLiveData<MutableList<Event>>()
    private var events : MutableList<Event> = arrayListOf()
    val filteredEventsLD = MutableLiveData<MutableList<Event>>()
    private var filteredEvents : MutableList<Event> = arrayListOf()

    private var isFilterActive:Boolean = false

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
            val eventsList = eventRepository.getAllEvents()

            withContext(Dispatchers.Main){
                events = eventsList
                eventsLD.value = events
                sortEventsByDateDescending()
            }
        }
    }

    fun sortEventsByDateDescending(){
        viewModelScope.launch(Dispatchers.IO) {
            events.sortByDescending {
                it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
            withContext(Dispatchers.Main){
                eventsLD.value = events
            }
        }
    }

    fun filterEventsByName(name:String){
        viewModelScope.launch (Dispatchers.IO){
            val filteredEventsList = arrayListOf<Event>()
            events.forEach{
                if(it.name.contains(name,ignoreCase = true)){
                    filteredEventsList.add(it)
                }
            }
            withContext(Dispatchers.Main){
                filteredEvents = filteredEventsList
                filteredEventsLD.value = filteredEvents
            }
            isFilterActive = true
        }
    }

    fun clearFilter(){
        filteredEvents = arrayListOf<Event>()
        filteredEventsLD.value = filteredEvents
        isFilterActive = false
    }

    /**
     * Filter events by isActive. An event is active if its date is after current date.
     */
    fun filterEventsByActive(isActive:Boolean){
        viewModelScope.launch (Dispatchers.IO){
            val filteredEventsList = arrayListOf<Event>()

            if(isActive){
                eventsLD.value?.forEach{
                    if(it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isAfter(LocalDateTime.now())||
                        it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isEqual(LocalDateTime.now())){
                        filteredEventsList.add(it)
                    }
                }
            }
            else{
                eventsLD.value?.forEach{
                    if(it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now())){
                        filteredEventsList.add(it)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                filteredEvents = filteredEventsList
                filteredEventsLD.value = filteredEvents
            }
            isFilterActive = true
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch (Dispatchers.IO){
            eventRepository.deleteEvent(event.id)

            withContext(Dispatchers.Main){
                events.remove(event)
                eventsLD.value = events

                if(isFilterActive)
                    filteredEvents.remove(event)
                    filteredEventsLD.value = filteredEvents
            }
        }
    }
}