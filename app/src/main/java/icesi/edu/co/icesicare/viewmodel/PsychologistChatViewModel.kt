package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Chat
import icesi.edu.co.icesicare.model.entity.Message
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.PsychologistChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PsychologistChatViewModel : ViewModel() {

    val chatLV = MutableLiveData<Chat>()
    private var psychologistChatRepository = PsychologistChatRepository()
    val studentLV = MutableLiveData<Student>()

    fun getChat(chatId : String){

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                studentLV.value = psychologistChatRepository.getContact(chatId, Firebase.auth.currentUser!!.uid)
            }
        }
    }

    fun sendMessage(chatId: String, message : Message){
        viewModelScope.launch(Dispatchers.IO) {
            psychologistChatRepository.sendMessage(chatId, message)
        }
    }
}