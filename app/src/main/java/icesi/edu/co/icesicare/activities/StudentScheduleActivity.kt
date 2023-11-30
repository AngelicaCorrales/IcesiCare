package icesi.edu.co.icesicare.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityStudentScheduleBinding
import icesi.edu.co.icesicare.view.fragments.AppointmentspsychologistFragment
import icesi.edu.co.icesicare.view.fragments.NoAppointmentsFragment
import icesi.edu.co.icesicare.viewmodel.AppointmentsListViewModel


class StudentScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentScheduleBinding
    val viewmodel: AppointmentsListViewModel by viewModels()


    val nothingFragment = NoAppointmentsFragment.newInstance()
    val appointmentsFragment = AppointmentspsychologistFragment.newInstance()
/*

debo poner un valor en el viewmodel para guardar el valor segun el boton


 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentScheduleBinding.inflate(layoutInflater)
        binding.btnAppointment.isEnabled = false
        binding.btnEvents.setOnClickListener {
            binding.btnAppointment.isEnabled = true
            binding.btnAppointment.setTextColor(Color.parseColor("#7802D4"))
            binding.btnEvents.isEnabled = false
            binding.btnEvents.setTextColor(Color.WHITE)
        }
        binding.btnAppointment.setOnClickListener {
            binding.btnEvents.isEnabled = true
            binding.btnEvents.setTextColor(Color.parseColor("#7802D4"))
            binding.btnAppointment.isEnabled = false
            binding.btnAppointment.setTextColor(Color.WHITE)

        }

        setContentView(binding.root)

        binding.tablayout.tabGravity = TabLayout.GRAVITY_FILL


        viewmodel.downloadAppointments(Firebase.auth.currentUser!!.uid)


        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewmodel.setMonth(tab!!.position+1)
                viewmodel.downloadAppointments(Firebase.auth.currentUser!!.uid)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewmodel.appointmentsListLiveData.observe(this){appointmentsList ->
            if (appointmentsList.size > 0) {
                loadFragment(appointmentsFragment)
            } else {
                loadFragment(nothingFragment)
            }
        }
    }

    private fun loadFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}
