package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.databinding.FirstScreenFragmentBinding

class FirstScreenFragment : Fragment() {

    lateinit var binding: FirstScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstScreenFragmentBinding.inflate (inflater, container, false)

        binding.firstSignUpBtn.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupFragment)
        }

        binding.firstSignInBtn.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signinFragment)
    }

        return binding.root
}

    companion object {
        @JvmStatic
        fun newInstance() = FirstScreenFragment()
    }
}
