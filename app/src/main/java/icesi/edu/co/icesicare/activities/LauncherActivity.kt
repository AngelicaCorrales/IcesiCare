package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        binding.signbtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.logbtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}