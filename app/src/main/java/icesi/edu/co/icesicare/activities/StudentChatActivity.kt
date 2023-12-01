package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.ActivityStudentChatBinding
import icesi.edu.co.icesicare.viewmodel.StudentChatViewModel

class StudentChatActivity : AppCompatActivity() {

    private val viewModel : StudentChatViewModel by viewModels()

    private val binding by lazy {
        ActivityStudentChatBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val chatId : String? = intent.extras?.getString("chatId")

        viewModel.getChat(chatId!!)
        viewModel.chatLV.observe(this){

        }
        viewModel.psychologist.observe(this){
            binding.contactName.text = it.name

            if(it.profileImageURL != ""){
                Glide.with(this).load(it.profileImageURL).into(binding.contactImage)
            }
        }
    }
}