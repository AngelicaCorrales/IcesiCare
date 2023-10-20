package icesi.edu.co.icesicare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import icesi.edu.co.icesicare.databinding.ActivityMainBinding
import icesi.edu.co.icesicare.view.fragments.AppointmentspsychologistFragment
import icesi.edu.co.icesicare.view.fragments.NoAppointmentsFragment

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val appointmentspsychologist = AppointmentspsychologistFragment()
    val noAppointments = NoAppointmentsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadFragment(appointmentspsychologist)


    }

    private fun loadFragment(profileFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.viewPager, profileFragment).commit()
    }
}