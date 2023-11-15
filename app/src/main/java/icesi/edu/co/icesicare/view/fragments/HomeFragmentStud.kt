package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentHomePsychBinding

class HomeFragmentStud : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomePsychBinding =
            FragmentHomePsychBinding.inflate(inflater,container,false)

        binding.acceptApptBtn.setOnClickListener {
            val intent= Intent(activity, AcceptAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }


        return inflater.inflate(R.layout.fragment_home_psych, container, false)
    }

    companion object {
        fun newInstance() = HomeFragmentPsych()
    }
}