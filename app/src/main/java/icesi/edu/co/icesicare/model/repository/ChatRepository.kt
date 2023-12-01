package icesi.edu.co.icesicare.model.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.model.entity.ChatData
import icesi.edu.co.icesicare.model.entity.ChatID
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository {

    /**
     * This function assumes the student and psychologist exist
     */
    suspend fun initializeChat(studId: String, psychId: String) {

        //Check if both dont have a chat, if they have, dont create new one

        val result1 = Firebase.firestore.collection("students")
            .document(studId)
            .collection("chats")
            .whereEqualTo("contactId",psychId)
            .get().await()

        if (!result1.isEmpty) return

        val result2 = Firebase.firestore.collection("students")
            .document(psychId)
            .collection("chats")
            .whereEqualTo("contactId",studId)
            .get().await()
        if (!result2.isEmpty) return

        val chatId = UUID.randomUUID().toString()

        Firebase.firestore.collection("chats")
            .document(chatId).set(ChatID(id=chatId)).await()

        Firebase.firestore.collection("students")
            .document(studId)
            .collection("chats")
            .document(chatId)
            .set(ChatData(chatId,psychId))
            .await()

        Firebase.firestore.collection("psychologists")
            .document(psychId)
            .collection("chats")
            .document(chatId)
            .set(ChatData(chatId,studId))
            .await()
    }
}
