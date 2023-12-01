package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityPsychologistChatBinding
import icesi.edu.co.icesicare.model.entity.Message
import icesi.edu.co.icesicare.view.fragments.PsychologistChatFragment
import icesi.edu.co.icesicare.viewmodel.PsychologistChatViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.TimeZone

class PsychologistChatActivity : AppCompatActivity() {
    private val viewModel : PsychologistChatViewModel by viewModels()
    private lateinit var listener: ListenerRegistration

    private val binding by lazy {
        ActivityPsychologistChatBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val chatId : String? = intent.extras?.getString("chatId")

        viewModel.getChat(chatId!!)
        viewModel.chatLV.observe(this){

        }
        viewModel.studentLV.observe(this){
            val name = it.name+" "+it.lastname
            binding.contactName.text = name

            if(it.profileImageURL != ""){
                Glide.with(this).load(it.profileImageURL).into(binding.contactImage)
            }
        }

        listener = Firebase.firestore.collection("chats").document(chatId).collection("messages").orderBy("date")
            .limitToLast(10)
            .addSnapshotListener{data, error ->

                for (doc in data!!.documentChanges){
                    if(doc.type == DocumentChange.Type.ADDED){
                        val message = doc.document.toObject(Message::class.java)
                        binding.messages.append(message.message +"\n\n")
                    }
                }
            }

        binding.backBtn.setOnClickListener {
            val intent= Intent(this, StudentMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.sendBtn.setOnClickListener {
            val message = binding.typeMess.text.toString()
            val messageToDb = Message(Firebase.auth.currentUser!!.uid, convertToDate(LocalDate.now()), "", message)
            viewModel.sendMessage(chatId, messageToDb)
            binding.typeMess.text.clear()
        }
    }

    private fun convertToDate(localDate: LocalDate): Date {
        val localDateTime = LocalDateTime.now()
        val zonaId = ZoneId.systemDefault()
        val date = localDateTime.atZone(zonaId).toInstant()
        return Date.from(date)
    }
}