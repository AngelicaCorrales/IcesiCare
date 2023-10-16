package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.FragmentMakeAppointmentPsychListBinding

class MakeAppointmentPsychListFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding: FragmentMakeAppointmentPsychListBinding=FragmentMakeAppointmentPsychListBinding.inflate(inflater,container,false)

        return binding.root
    }

    companion object {
        fun newInstance():MakeAppointmentPsychListFragment{
           return MakeAppointmentPsychListFragment()
       }
    }
}