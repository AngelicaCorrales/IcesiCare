package icesi.edu.co.icesicare.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.dto.out.MessageOutDTO
import icesi.edu.co.icesicare.model.entity.Message
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.model.repository.StudentChatRepository
import icesi.edu.co.icesicare.model.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class StudentChatViewModel : ViewModel() {

    val messagesLV = MutableLiveData<ArrayList<MessageOutDTO>>()
    private var studentChatRepository = StudentChatRepository()
    val psychologist = MutableLiveData<Psychologist>()
    val currentUser = Firebase.auth.currentUser!!.uid
    val messages : ArrayList<MessageOutDTO> = ArrayList()

    fun getChat(chatId : String){

        viewModelScope.launch(Dispatchers.IO) {
            val chatOfDb = studentChatRepository.getChat(chatId)

            withContext(Dispatchers.Main){
                psychologist.value = studentChatRepository.getContact(chatId, currentUser)
            }

            for (message in chatOfDb.messages!!.sortedBy { it.date }){

                if (message.authorId == currentUser){
                    messages.add(MessageOutDTO("Tú", formatHour(message.date!!), message.message, currentUser))

                }else{
                    val psychologist = PsychRepository.getPsychologist(message.authorId!!)
                    messages.add(MessageOutDTO(psychologist.name, formatHour(message.date!!), message.message, psychologist.id))
                }
            }

            withContext(Dispatchers.Main){
                messagesLV.value = messages
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

    fun sendMessage(chatId: String, message : Message){
        viewModelScope.launch(Dispatchers.IO) {
            val messageSend = studentChatRepository.sendMessage(chatId, message)

            if (messageSend.authorId == currentUser){
                messages.add(MessageOutDTO("Tú", formatHour(messageSend.date!!), messageSend.message, currentUser))

            }else{
                val psychologist = PsychRepository.getPsychologist(messageSend.authorId!!)
                messages.add(MessageOutDTO(psychologist.name, formatHour(messageSend.date!!), messageSend.message, psychologist.id))
            }
            withContext(Dispatchers.Main){
                messagesLV.value = messages
            }
        }
    }
}