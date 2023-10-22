package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityUserAppointmentInfoBinding

class UserAppointmentInfo : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserAppointmentInfoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}