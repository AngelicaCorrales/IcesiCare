package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.MakeEventsActivity
import icesi.edu.co.icesicare.databinding.FragmentHomePsychBinding
import icesi.edu.co.icesicare.viewmodel.PsychologistViewModel

class HomeFragmentPsych : Fragment() {

    private lateinit var binding: FragmentHomePsychBinding
    private val viewModel : PsychologistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomePsychBinding.inflate(inflater,container,false)

        viewModel.getPsychologist(Firebase.auth.currentUser!!.uid)
        viewModel.psychologistLV.observe(viewLifecycleOwner){
            val welcomeUser = "Hola, "+it?.name
            binding.helloTv.text = welcomeUser

            if (it?.genre == "Femenino"){
                binding.welcomeTV.text = "Bienvenida"

            }else if (it?.genre == "Otro"){
                binding.welcomeTV.text = "Bienvenide"
            }
        }

        binding.acceptApptBtn.setOnClickListener {
            val intent= Intent(activity, AcceptAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }
        binding.eventsBtn.setOnClickListener {
            val intent= Intent(activity, MakeEventsActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragmentPsych()
    }
}