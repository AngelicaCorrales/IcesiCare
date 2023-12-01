package icesi.edu.co.icesicare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.icesicare.model.repository.ChatRepository
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val chatRepository = ChatRepository()

    fun initializeChat(studId:String,psychId:String){
        viewModelScope.launch (Dispatchers.IO){
            chatRepository.initializeChat(studId,psychId)
        }
    }


}