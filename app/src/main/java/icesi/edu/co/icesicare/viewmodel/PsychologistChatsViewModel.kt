package icesi.edu.co.icesicare.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.dto.out.ChatOutDTO
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.model.repository.PsychologistChatRepository
import icesi.edu.co.icesicare.model.repository.StudentRepository
import icesi.edu.co.icesicare.model.service.GoObjectDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class PsychologistChatsViewModel : ViewModel(), GoObjectDetail {

    val chatSLV = MutableLiveData<ArrayList<ChatOutDTO>>()
    private var psychologistChatViewModel = PsychologistChatRepository()
    val chatId = MutableLiveData<String>()
    private var studentRepository = StudentRepository()

    fun getChats(psychologistId : String){

        val chats = ArrayList<ChatOutDTO>()

        viewModelScope.launch(Dispatchers.IO) {
            val chatsOfDb = psychologistChatViewModel.getChatsFromPsychologist(psychologistId)

            for (chat in chatsOfDb){
                val contact = studentRepository.getStudent(chat.contactId)
                val lastMessage = psychologistChatViewModel.getLastMessageFromChat(chat.id)

                val newChat = ChatOutDTO(contact.name, contact.profileImageURL!!,
                    lastMessage.message, formatHour(lastMessage.date!!), chat.id)

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

    override fun onItemClick(objectDetail: String) {
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                chatId.value = objectDetail
            }
        }
    }
}