package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.dto.`in`.ChatInDTO
import icesi.edu.co.icesicare.model.entity.Chat
import icesi.edu.co.icesicare.model.entity.Message
import icesi.edu.co.icesicare.model.entity.Student
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PsychologistChatRepository {

    private val studentRepository = StudentRepository()

    suspend fun getChatsFromPsychologist(psychologistId : String) : ArrayList<ChatInDTO> {

        val chats = ArrayList<ChatInDTO>()

        val document = Firebase.firestore.collection("psychologists").document(psychologistId)
            .collection("chats").get().await()

        for(docChat in document.documents){
            val chat = docChat.toObject(ChatInDTO::class.java)

            if (chat != null) {
                chats.add(chat)
            }
        }
        return chats
    }

    suspend fun getChat(chatId : String) : Chat {
        val docChat = Firebase.firestore.collection("chats").document(chatId).get().await()
        val chat = docChat.toObject(Chat::class.java)
        chat!!.messages = getMessages(chatId)

        return chat
    }

    private suspend fun getMessages(chatId: String): List<Message> {
        val docMessages = Firebase.firestore.collection("chats").document(chatId)
            .collection("messages").get().await()

        return docMessages.toObjects(Message::class.java)
    }

    suspend fun getContact(chatId: String, psychologistId: String) : Student {
        val docChat = Firebase.firestore.collection("psychologists").document(psychologistId).collection("chats")
            .document(chatId).get().await()

        val chat = docChat.toObject(ChatInDTO::class.java)

        return studentRepository.getStudent(chat!!.contactId)
    }

    suspend fun getLastMessageFromChat(chatId : String) : Message {

        var message = Message(null, null, null, "Empiece una conversación")

        val docMessage = Firebase.firestore.collection("chats").document(chatId)
            .collection("messages").orderBy("date").limitToLast(1).get().await()

        for (lastMessages in docMessage.documents){
            message = lastMessages.toObject(Message::class.java)!!
        }

        return message
    }

    suspend fun sendMessage(chatId: String, message: Message) : Message{

        val messageId = UUID.randomUUID()
        message.id = messageId.toString()

        Firebase.firestore.collection("chats")
            .document(chatId).collection("messages").document(messageId.toString()).set(message).await()

        return message
    }
}