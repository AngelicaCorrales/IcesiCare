package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.activities.PsychologistMainActivity
import icesi.edu.co.icesicare.activities.StudentMainActivity
import icesi.edu.co.icesicare.databinding.FragmentSignInBinding
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.model.repository.StudentRepository
import icesi.edu.co.icesicare.viewmodel.AuthViewModel

class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val vm: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.signupLink.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupFragment)
        }
        binding.loginBtn.setOnClickListener {
            vm.signIn(
                binding.emailET.text.toString(),
                binding.passET.text.toString()
            )
        }

        //Observer
        vm.authStateLV.observe(viewLifecycleOwner){ state ->
            if(state.isAuth){
                sendToCorrectActivity(state.userID.toString())
            }
        }
        vm.errorLV.observe(viewLifecycleOwner){error->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun sendToCorrectActivity(userId : String){
        Log.e("a", vm.getRoleOfLoggedStudent(userId).toString())
        if (vm.getRoleOfLoggedStudent(userId) is Student){
            startActivity(Intent(requireContext(), StudentMainActivity::class.java))

        }else{
            startActivity(Intent(requireContext(), PsychologistMainActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}