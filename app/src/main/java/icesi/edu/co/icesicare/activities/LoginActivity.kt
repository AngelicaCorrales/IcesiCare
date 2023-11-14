package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.media3.common.util.NotificationUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //NotificationUtil.showNotification(this, "Nuevo", "Mensaje")

        //Firebase.messaging.suscribeToTopic("promo")

        binding.loginBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val pass = binding.passET.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {

                val fbuser = Firebase.auth.currentUser
                if(fbuser!!.isEmailVerified) {

                    Firebase.firestore.collection("students").document(fbuser.uid).get().addOnSuccessListener {
                        val user = it.toObject(User::class.java)
                        saveUser(user!!)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

            }else{
                Toast.makeText(this, "Su email no está verificado", Toast.LENGTH_LONG).show()

                }

            }.addOnFailureListener{
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

        binding.registro.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    }

    private fun saveUser(user: User) {
        val sp = getSharedPreferences("icesicare", MODE_PRIVATE)
        //val json = Gson().toJson(user)
        //sp.edit().putString("user", json).apply()

    }
}






