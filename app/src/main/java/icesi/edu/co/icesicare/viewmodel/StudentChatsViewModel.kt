package icesi.edu.co.icesicare.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.dto.ChatDTO
import icesi.edu.co.icesicare.model.entity.Chat
import icesi.edu.co.icesicare.model.repository.ChatRepository
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentChatsViewModel : ViewModel(){

    val chatSLV = MutableLiveData<ArrayList<ChatDTO>>()
    private var chatRepository = ChatRepository()
    //private var studentRepository = StudentRepository()

    fun getChats(){

        val chats = ArrayList<ChatDTO>()

        viewModelScope.launch(Dispatchers.IO) {
            val chatsOfDb = chatRepository.getChats()

            for (chat in chatsOfDb){
                val newChat = ChatDTO(chat.id, "", "ultimo", "12:10")
                chats.add(newChat)
            }

            withContext(Dispatchers.Main){
                chatSLV.value = chats
            }
        }
    }
}