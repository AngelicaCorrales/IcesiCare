package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.entity.Chat
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentChatViewModel : ViewModel() {

    val chatLV = MutableLiveData<Chat>()
    private var chatRepository = ChatRepository()
    val psychologist = MutableLiveData<Psychologist>()

    fun getChat(chatId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val chatOfDb = chatRepository.getChat(chatId)

            for (message in chatOfDb.messages!!){

                withContext(Dispatchers.Main){
                    psychologist.value = chatRepository.getContact(chatId, message.authorId.toString())
                }
                withContext(Dispatchers.Main){
                    chatLV.value = chatOfDb
                }
                break
            }
        }
    }
}