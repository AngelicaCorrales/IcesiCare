package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentHomePsychBinding
import icesi.edu.co.icesicare.databinding.FragmentHomeStudBinding

class HomeFragmentStud : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeStudBinding =
            FragmentHomeStudBinding.inflate(inflater,container,false)

        binding.makeAppmBtn.setOnClickListener {
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }


        return inflater.inflate(R.layout.fragment_home_stud, container, false)
    }

    companion object {
        fun newInstance() = HomeFragmentStud()
    }
}