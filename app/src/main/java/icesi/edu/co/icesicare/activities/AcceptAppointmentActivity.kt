package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAcceptAppointmentBinding
import icesi.edu.co.icesicare.view.fragments.AcceptAppointmentFragment

class AcceptAppointmentActivity : AppCompatActivity() {

    private val acceptApptFragment = AcceptAppointmentFragment.newInstance()

    private val binding by lazy {
        ActivityAcceptAppointmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            val intent = Intent(this,PsychologistMainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        showFragment()
    }

    fun showFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,acceptApptFragment).commit()
    }
}