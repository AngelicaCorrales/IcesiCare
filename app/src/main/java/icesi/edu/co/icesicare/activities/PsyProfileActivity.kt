package icesi.edu.co.icesicare.activities

import android.os.Bundle
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.ActivityPsyprofileBinding
import icesi.edu.co.icesicare.view.fragments.EditPsyProfileFragment

class PsyProfileActivity : AppCompatActivity()  {

    private val binding by lazy {
        ActivityPsyprofileBinding.inflate(layoutInflater)
    }

    private val editPsyProfileFragment by lazy {
        EditPsyProfileFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadFragment(editPsyProfileFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, fragment).commit()
    }
}