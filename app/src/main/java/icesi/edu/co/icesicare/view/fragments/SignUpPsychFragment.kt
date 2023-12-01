package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.activities.PsychologistMainActivity
import icesi.edu.co.icesicare.databinding.FragmentSignUpPsychBinding
import icesi.edu.co.icesicare.viewmodel.AuthViewModel

class SignUpPsychFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPsychBinding
    private val vm: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPsychBinding.inflate(inflater, container, false)

        var genre=""
        val genreSpinner= binding.genreSpin
        val genres= arrayOf("Femenino","Masculino","Otro")
        val arrayAdp= ArrayAdapter(requireContext(), R.layout.genre_list,genres)
        arrayAdp.setDropDownViewResource(R.layout.genre_list)
        genreSpinner.adapter=arrayAdp


        genreSpinner?.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                genre= genres[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                genre=""
            }

        }


        binding.haveAccountLink.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signinFragment)
        }
        binding.signupBtn.setOnClickListener {

            vm.signupPsych(
                binding.fullNameET.text.toString(),
                genre,
                binding.emailAddrET.text.toString(),
                binding.passwordET.text.toString(),
                binding.confirmPasswordET.text.toString()
            )
        }

        binding.backBtnSignUpPsy.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupFragment)
        }

        //Observer
        vm.authStateLV.observe(viewLifecycleOwner){ state ->
            if(state.isAuth){
                startActivity(Intent(requireContext(), PsychologistMainActivity::class.java))
            }
        }
        vm.errorLV.observe(viewLifecycleOwner){error->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpPsychFragment()
    }
}