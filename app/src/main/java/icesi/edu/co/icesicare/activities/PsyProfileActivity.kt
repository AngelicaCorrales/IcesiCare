package icesi.edu.co.icesicare.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityPsyprofileBinding
import icesi.edu.co.icesicare.view.fragments.EditPsyProfileFragment
import icesi.edu.co.icesicare.view.fragments.EditPsyScheduleFragment

class PsyProfileActivity : AppCompatActivity()  {

    private val editPsyProfileFragment = EditPsyProfileFragment.newInstance()
    private val editScheduleFragment = EditPsyScheduleFragment.newInstance()
    private val binding by lazy {
        ActivityPsyprofileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showLoading(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,editPsyProfileFragment).commit()

    }

    fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE

        val negationVisibility = if (isLoading) View.INVISIBLE else View.VISIBLE

        binding.fragmentContainer.visibility = negationVisibility
        for (i in 0 until binding.fragmentContainer.childCount) {
            val child = binding.fragmentContainer.getChildAt(i)
            child.visibility = negationVisibility
        }
    }

    private fun showFragment(fragment: Fragment){
        showLoading(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }

    fun showEditProfileFragment(){
        showLoading(true)
        showFragment(editPsyProfileFragment)
    }

    fun showEditPsyScheduleFragment(){
        showLoading(true)
        showFragment(editScheduleFragment)
    }


}