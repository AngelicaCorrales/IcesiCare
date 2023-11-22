package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentHomeStudBinding
import icesi.edu.co.icesicare.viewmodel.StudentProfileViewModel

class HomeFragmentStud : Fragment() {

    private lateinit var binding: FragmentHomeStudBinding
    private val viewModel : StudentProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeStudBinding.inflate(inflater,container,false)

        viewModel.getStudent(Firebase.auth.currentUser!!.uid)
        viewModel.studentLV.observe(viewLifecycleOwner){
            val welcomeUser = "Hola, "+it.name
            binding.helloTv.text = welcomeUser

            if (it.genre == "Femenino"){
                binding.welcomeTV.text = "Bienvenida"

            }else if (it.genre == "Otro"){
                binding.welcomeTV.text = "Bienvenide"
            }
        }

        binding.makeAppmBtn.setOnClickListener {
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragmentStud()
    }
}