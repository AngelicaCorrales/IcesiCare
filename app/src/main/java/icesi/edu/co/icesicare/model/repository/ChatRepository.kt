package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.dto.`in`.ChatInDTO
import icesi.edu.co.icesicare.model.entity.Message
import kotlinx.coroutines.tasks.await

class ChatRepository {

    suspend fun getChatsFromStudent(studentId : String) : ArrayList<ChatInDTO> {

        val chats = ArrayList<ChatInDTO>()

        val document = Firebase.firestore.collection("students").document(studentId)
            .collection("chats").get().await()

        for(docChat in document.documents){
            val chat = docChat.toObject(ChatInDTO::class.java)

            if (chat != null) {
                chats.add(chat)
            }
        }
        return chats
    }

    suspend fun getLastMessageFromChat(chatId : String) : Message{

        var message = Message(null, null, null, "Empiece una conversación")

        val docMessage = Firebase.firestore.collection("chats").document(chatId)
            .collection("messages").orderBy("date").limitToLast(1).get().await()

        for (lastMessages in docMessage.documents){
            message = lastMessages.toObject(Message::class.java)!!
        }

        return message
    }
}