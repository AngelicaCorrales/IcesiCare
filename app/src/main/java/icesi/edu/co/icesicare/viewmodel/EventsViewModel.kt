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
import java.util.Date

class EventViewModel : ViewModel() {

    val eventLV = MutableLiveData<Event?>()

    val eventsLD = MutableLiveData<MutableList<Event>>()
    private var events : MutableList<Event> = arrayListOf()

    val eventLD = MutableLiveData<Event?>()

    val filteredEventsLD = MutableLiveData<MutableList<Event>>()
    private var filteredEvents : MutableList<Event> = arrayListOf()
    private var isFilterActive:Boolean = false

    private var eventRepository: EventRepository = EventRepository()

    val errorLD = MutableLiveData<Exception>()

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
            try {
                val eventsList = eventRepository.getAllEvents()

                withContext(Dispatchers.Main){
                    events = eventsList
                    eventsLD.value = events
                    sortEventsByDateDescending()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }



    private fun sortEventsByDateDescending(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                events.sortByDescending {
                    it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                }

                if(isFilterActive)
                    filteredEvents.sortByDescending {
                        it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    }

                withContext(Dispatchers.Main){
                    eventsLD.value = events
                    if(isFilterActive)
                        filteredEventsLD.value = filteredEvents
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun filterEventsByName(name:String){
        viewModelScope.launch (Dispatchers.IO){
            try {
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
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun clearFilter(){
        viewModelScope.launch (Dispatchers.IO) {
            filteredEvents = arrayListOf()
            withContext(Dispatchers.Main) {
                filteredEventsLD.value = filteredEvents
            }
            isFilterActive = false
        }
    }

    /**
     * Filter events by isActive. An event is active if its date is after current date.
     */
    fun filterEventsByActive(isActive:Boolean){
        viewModelScope.launch (Dispatchers.IO){
            try {
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
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch (Dispatchers.IO){
            try {
                eventRepository.deleteEvent(event.id)

                withContext(Dispatchers.Main){
                    events.remove(event)
                    eventsLD.value = events

                    if(isFilterActive){
                        filteredEvents.remove(event)
                        filteredEventsLD.value = filteredEvents
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun updateEvent(event:Event, isImageChanged:Boolean){
        viewModelScope.launch (Dispatchers.IO){

            try {
                eventRepository.updateEvent(event,isImageChanged)

                val updatedEvent = eventRepository.getEvent(event.id)

                withContext(Dispatchers.Main){
                    eventLD.postValue(updatedEvent)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }

    fun createEvent(category: String, date : Date, name : String, imageUrl:String, space : String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val event = Event(category,date,"","",imageUrl,name,space)
                val newEventId = eventRepository.createEvent(event)
                val newEvent = eventRepository.getEvent(newEventId)

                withContext(Dispatchers.Main){
                    eventLD.postValue(newEvent)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    errorLD.value = e
                }
            }
        }
    }
}