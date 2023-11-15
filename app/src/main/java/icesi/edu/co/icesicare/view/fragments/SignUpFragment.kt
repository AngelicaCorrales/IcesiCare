package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.activities.MainActivity
import icesi.edu.co.icesicare.databinding.FragmentSignUpBinding
import icesi.edu.co.icesicare.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val vm: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.haveAccountLink.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signinFragment)
        }

        binding.signupPsychBtn.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupPsychFragment)
        }

        binding.signupStudBtn.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupStudFragment)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}