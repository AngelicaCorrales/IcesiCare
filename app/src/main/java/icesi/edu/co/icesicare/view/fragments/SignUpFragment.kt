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

        var genre=""
        val genreSpinner= binding.genreSpin
        val genres= arrayOf("Femenino","Masculino","Otro")
        val arrayAdp=ArrayAdapter(requireContext(), R.layout.genre_list,genres)
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

            vm.signup(
                binding.fullNameET.text.toString(),
                genre,
                binding.emailAddrET.text.toString(),
                binding.passwordET.text.toString()
            )
        }

        //Observer
        vm.authStateLV.observe(viewLifecycleOwner){ state ->
            if(state.isAuth){
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
        vm.errorLV.observe(viewLifecycleOwner){error->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}