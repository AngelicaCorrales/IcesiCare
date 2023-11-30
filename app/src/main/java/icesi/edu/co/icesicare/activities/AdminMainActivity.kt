    package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminMainBinding
import icesi.edu.co.icesicare.databinding.ActivityPsychologistMainBinding

    class AdminMainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAdminMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.adminPsychsBtn.setOnClickListener{
            startActivity(Intent(this, AdminPsychologistsActivity::class.java))
            this.finish()
        }

        binding.adminEventsBtn.setOnClickListener{
            startActivity(Intent(this, AdminEventsActivity::class.java))
            this.finish()
        }
    }
}