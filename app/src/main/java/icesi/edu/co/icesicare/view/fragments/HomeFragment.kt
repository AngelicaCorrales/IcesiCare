package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.HomeFragmentBinding

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: HomeFragmentBinding=HomeFragmentBinding.inflate(inflater,container,false)

        binding.makeAppmBtn.setOnClickListener {
            val intent=Intent(activity,MakeAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }

        binding.acceptApptBtn.setOnClickListener {
            val intent=Intent(activity,AcceptAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }

        return binding.root
    }

    companion object{
        fun newInstance():HomeFragment{
            return HomeFragment()
        }
    }
}