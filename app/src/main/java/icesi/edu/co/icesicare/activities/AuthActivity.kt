package icesi.edu.co.icesicare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.databinding.ActivityAuthBinding
import icesi.edu.co.icesicare.view.fragments.FirstScreenFragment
import icesi.edu.co.icesicare.view.fragments.SignInFragment
import icesi.edu.co.icesicare.view.fragments.SignUpFragment
import icesi.edu.co.icesicare.view.fragments.SignUpPsychFragment
import icesi.edu.co.icesicare.view.fragments.SignUpStudFragment


class AuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    val firstscreenFragment by lazy {
        FirstScreenFragment.newInstance()
    }

    val signinFragment by lazy {
        SignInFragment.newInstance()
    }

    val signupFragment by lazy {
        SignUpFragment.newInstance()
    }

    val signupPsychFragment by lazy {
        SignUpPsychFragment.newInstance()
    }

    val signupStudFragment by lazy {
        SignUpStudFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Firebase.auth.signOut()
        loadFragment(firstscreenFragment)
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment).commit()
    }
}