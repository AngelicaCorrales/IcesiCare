package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityMakeAppointmentBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.view.fragments.MakeAppointmentPsychDetailFragment
import icesi.edu.co.icesicare.view.fragments.MakeAppointmentPsychListFragment

class MakeAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMakeAppointmentBinding.inflate(layoutInflater)
    }

    private val makeAppntPsychListFragment= MakeAppointmentPsychListFragment.newInstance()
    private val makeAppntPsychDetailFragment = MakeAppointmentPsychDetailFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragmentPsychList()
    }

    fun showFragmentPsychList(){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,makeAppntPsychListFragment).commit()
    }

    fun showFragmentPsychDetail(psych:Psychologist?){
        makeAppntPsychDetailFragment.psych = psych
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,makeAppntPsychDetailFragment).commit()
    }
}