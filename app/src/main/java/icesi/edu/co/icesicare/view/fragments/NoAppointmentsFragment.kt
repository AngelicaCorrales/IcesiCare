package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentNoAppointmentsBinding
import icesi.edu.co.icesicare.databinding.StudentProfileFragmentBinding

class NoAppointmentsFragment : Fragment() {

    private lateinit var binding: FragmentNoAppointmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoAppointmentsBinding.inflate(inflater,container,false)

        binding.searchPsyBtn.setOnClickListener {
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoAppointmentsFragment()
    }
}
