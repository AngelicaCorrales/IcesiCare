package icesi.edu.co.icesicare.model.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.Chat
import kotlinx.coroutines.tasks.await

class ChatRepository {

    suspend fun getChats() : ArrayList<Chat> {

        val chats = ArrayList<Chat>()

        val document = Firebase.firestore.collection("chats").get().await()

        for(docChat in document.documents){
            val chat = docChat.toObject(Chat::class.java)

            if (chat != null) {
                chats.add(chat)
            }
        }
        return chats
    }
}