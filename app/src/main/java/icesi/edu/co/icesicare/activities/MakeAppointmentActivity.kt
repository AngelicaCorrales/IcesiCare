package icesi.edu.co.icesicare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
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

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchBtn.setOnClickListener{
            binding.psychTV.visibility = View.GONE
            binding.backBtn.visibility = View.GONE
            binding.searchBtn.visibility = View.GONE
            binding.cancelSearchBtn.visibility = View.VISIBLE
            binding.searchView.visibility = View.VISIBLE
            binding.searchView.requestFocus()

            imm.showSoftInput(binding.searchView, 0)
        }

        //Only works when query is not empty due to null check in the implementation of SearchView
        binding.searchView.setOnSearchClickListener{
            imm.hideSoftInputFromWindow(binding.searchView.windowToken,0)
        }

        binding.cancelSearchBtn.setOnClickListener{
            binding.psychTV.visibility = View.VISIBLE
            binding.backBtn.visibility = View.VISIBLE
            binding.searchBtn.visibility = View.VISIBLE
            binding.cancelSearchBtn.visibility = View.GONE
            binding.searchView.visibility = View.GONE
            binding.searchView.setQuery("",false)
            makeAppntPsychListFragment.clearFilter()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    makeAppntPsychListFragment.filterPsychsByName(newText)
                    return true
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    makeAppntPsychListFragment.filterPsychsByName(query)
                    return true
                }
                return false
            }
        })
    }

    fun showFragmentPsychList(){
        changeProgressBarVisibility(true)
        supportFragmentManager.beginTransaction().replace(R.id.progressBarContainer,makeAppntPsychListFragment).commit()

        binding.psychTV.visibility = View.VISIBLE
        binding.searchBtn.visibility = View.VISIBLE

        binding.backBtn.setOnClickListener{
            val intent= Intent(this,StudentMainActivity::class.java)
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

        supportFragmentManager.beginTransaction().replace(R.id.progressBarContainer,makeAppntPsychDetailFragment).commit()

        makeAppntPsychDetailFragment.psych = psych
        binding.psychTV.visibility = View.GONE
        binding.searchBtn.visibility = View.GONE

        binding.backBtn.setOnClickListener{
            showFragmentPsychList()
        }
    }
}