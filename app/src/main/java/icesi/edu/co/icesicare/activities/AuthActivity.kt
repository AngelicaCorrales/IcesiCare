package icesi.edu.co.icesicare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.ActivityAuthBinding
import icesi.edu.co.icesicare.view.fragments.SignInFragment
import icesi.edu.co.icesicare.view.fragments.SignUpFragment


class AuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    val signinFragment by lazy {
        SignInFragment.newInstance()
    }

    val signupFragment by lazy {
        SignUpFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadFragment(signinFragment)
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment).commit()
    }
}