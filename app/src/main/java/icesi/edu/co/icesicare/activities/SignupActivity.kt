package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivitySignupBinding
import icesi.edu.co.icesicare.model.entity.User

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.haveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupBtn.setOnClickListener(::register)
        }

        private fun register(view: View) {
            Firebase.auth.createUserWithEmailAndPassword(
                binding.emailTI.editText?.text.toString(),
                binding.passTI.editText?.text.toString()
            ).addOnSuccessListener {
                val id = Firebase.auth.currentUser?.uid

                val user = User(id!!,
                    binding.nameTI.editText?.text.toString()
                    , binding.emailTI.editText?.text.toString())

                Firebase.firestore.collection("psychologists").document(id).set(user);
            }
        }
    }
