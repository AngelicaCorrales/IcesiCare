package icesi.edu.co.icesicare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.IntroductionView1Binding
import icesi.edu.co.icesicare.view.fragments.IntroScreenFragment

class IntroductionActivity : AppCompatActivity() {

    private val binding by lazy {
        IntroductionView1Binding.inflate(layoutInflater)
    }

    private val introScreenFragment by lazy {
        IntroScreenFragment.newInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadFragment(introScreenFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentIntroContainer.id, fragment).commit()
    }
}