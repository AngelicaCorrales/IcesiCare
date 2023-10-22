package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityMakeAppointmentBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.view.fragments.MakeAppointmentPsychDetailFragment
import icesi.edu.co.icesicare.view.fragments.MakeAppointmentPsychListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        changeProgressBarVisibility(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,makeAppntPsychListFragment).commit()

        binding.psychTV.visibility = View.VISIBLE
        binding.searchBtn.visibility = View.VISIBLE
        binding.filterBtn.visibility = View.VISIBLE
        binding.helpBtn.visibility = View.VISIBLE

        binding.backBtn.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    fun changeProgressBarVisibility(isVisible:Boolean){
        if(isVisible)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    fun showFragmentPsychDetail(psych:Psychologist?){

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,makeAppntPsychDetailFragment).commit()

        makeAppntPsychDetailFragment.psych = psych
        binding.psychTV.visibility = View.GONE
        binding.searchBtn.visibility = View.GONE
        binding.filterBtn.visibility = View.GONE
        binding.helpBtn.visibility = View.GONE

        binding.backBtn.setOnClickListener{
            showFragmentPsychList()
        }
    }
}