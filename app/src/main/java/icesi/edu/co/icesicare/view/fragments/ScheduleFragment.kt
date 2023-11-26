package icesi.edu.co.icesicare.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityMainBinding
import icesi.edu.co.icesicare.databinding.ScheduleFragmentBinding
import icesi.edu.co.icesicare.viewmodel.AppointmentsListViewModel

class ScheduleFragment: Fragment() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: AppointmentsListViewModel by activityViewModels()

    private val nothingFragment = NoAppointmentsFragment.newInstance()
    private val appointmentsFragment = AppointmentspsychologistFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAppointment.isEnabled = false
        binding.btnEvents.setOnClickListener {
            binding.btnAppointment.isEnabled = true
            binding.btnAppointment.setTextColor(Color.parseColor("#7802D4"))
            binding.btnEvents.isEnabled = false
            binding.btnEvents.setTextColor(Color.WHITE)
        }
        binding.btnAppointment.setOnClickListener {
            binding.btnEvents.isEnabled = true
            binding.btnEvents.setTextColor(Color.parseColor("#7802D4"))
            binding.btnAppointment.isEnabled = false
            binding.btnAppointment.setTextColor(Color.WHITE)
        }

        binding.tablayout.tabGravity = TabLayout.GRAVITY_FILL

        viewmodel.downloadAppointments()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewmodel.setMonth(tab!!.position + 1)
                viewmodel.downloadAppointments()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewmodel.appointmentsListLiveData.observe(viewLifecycleOwner) { appointmentsList ->
            if (appointmentsList.size > 0) {
                Log.e(">>>", " cargando el fragment  ")
                loadFragment(appointmentsFragment)
            } else {
                loadFragment(nothingFragment)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null) // Opcional: agrega la transacción a la pila para manejar retroceso
        transaction.commit()

    }

    companion object {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }
}