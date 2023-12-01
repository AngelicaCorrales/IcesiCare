package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Chat
import icesi.edu.co.icesicare.model.entity.Message
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.StudentChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentChatViewModel : ViewModel() {

    val chatLV = MutableLiveData<Chat>()
    private var studentChatRepository = StudentChatRepository()
    val psychologist = MutableLiveData<Psychologist>()

    fun getChat(chatId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val chatOfDb = studentChatRepository.getChat(chatId)

            withContext(Dispatchers.Main){
                psychologist.value = studentChatRepository.getContact(chatId, Firebase.auth.currentUser!!.uid)
            }
            withContext(Dispatchers.Main){
                chatLV.value = chatOfDb
            }
        }
    }

    fun sendMessage(chatId: String, message : Message){
        viewModelScope.launch(Dispatchers.IO) {
            studentChatRepository.sendMessage(chatId, message)
        }
    }
}