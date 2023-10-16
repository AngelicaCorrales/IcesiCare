package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityMakeAppointmentBinding
import icesi.edu.co.icesicare.view.fragments.MakeAppointmentPsychListFragment

class MakeAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMakeAppointmentBinding.inflate(layoutInflater)
    }

    private val makeAppntPsychList=MakeAppointmentPsychListFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragment(makeAppntPsychList)

    }
    fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}