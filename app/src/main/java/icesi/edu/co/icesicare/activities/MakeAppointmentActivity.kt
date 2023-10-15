package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.databinding.ActivityMakeAppointmentBinding

class MakeAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMakeAppointmentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}