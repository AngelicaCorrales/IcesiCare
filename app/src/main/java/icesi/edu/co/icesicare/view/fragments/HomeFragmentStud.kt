package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentHomeStudBinding

class HomeFragmentStud : Fragment() {

    private lateinit var binding: FragmentHomeStudBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeStudBinding.inflate(inflater,container,false)


        binding.makeAppmBtn.setOnClickListener {
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }

        binding.eventsBtn.setOnClickListener { //Esto no debería ir aquí, sino cuando le de clic a un evento específico
            val dialog = EventDialogFragment()
            dialog.show(requireFragmentManager(), "event_dialog")
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragmentStud()
    }
}