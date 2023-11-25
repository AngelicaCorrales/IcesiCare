package icesi.edu.co.icesicare.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.dto.out.ChatOutDTO
import icesi.edu.co.icesicare.model.repository.ChatRepository
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class StudentChatsViewModel : ViewModel(){

    val chatSLV = MutableLiveData<ArrayList<ChatOutDTO>>()
    private var chatRepository = ChatRepository()

    fun getChats(studentId : String){

        val chats = ArrayList<ChatOutDTO>()

        viewModelScope.launch(Dispatchers.IO) {
            val chatsOfDb = chatRepository.getChatsFromStudent(studentId)

            for (chat in chatsOfDb){
                val contact = PsychRepository.getPsychologist(chat.contactId)
                val lastMessage = chatRepository.getLastMessageFromChat(chat.id)

                val newChat = ChatOutDTO(contact.name, contact.profileImageURL!!,
                    lastMessage.message, formatHour(lastMessage.date!!))

                chats.add(newChat)
            }

            withContext(Dispatchers.Main){
                chatSLV.value = chats
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatHour(date : Date) : String{
        val formatHour = SimpleDateFormat("HH:mm")

        val timeZone = TimeZone.getTimeZone("GMT-5")
        formatHour.timeZone = timeZone

        return formatHour.format(date)
    }
}