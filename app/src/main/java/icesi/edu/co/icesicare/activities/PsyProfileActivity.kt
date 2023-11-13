package icesi.edu.co.icesicare.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityPsyprofileBinding
import icesi.edu.co.icesicare.view.fragments.EditPsyProfileFragment

class PsyProfileActivity : AppCompatActivity()  {

    private val binding by lazy {
        ActivityPsyprofileBinding.inflate(layoutInflater)
    }

    private val editPsyProfileFragment = EditPsyProfileFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showLoading(true)
        supportFragmentManager.beginTransaction().replace(R.id.progressBarContainer,editPsyProfileFragment).commit()
    }

    fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}