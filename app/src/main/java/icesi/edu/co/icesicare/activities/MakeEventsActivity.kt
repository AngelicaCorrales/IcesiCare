package icesi.edu.co.icesicare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.FragmentEventsBinding

class MakeEventsActivity   : AppCompatActivity(){
    private lateinit var binding: FragmentEventsBinding

    //private val noEvents = MakeAppointmentPsychDetailFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tablayout.tabGravity = TabLayout.GRAVITY_FILL

    }

}