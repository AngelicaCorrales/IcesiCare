package icesi.edu.co.icesicare.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityStudentScheduleBinding
import icesi.edu.co.icesicare.viewmodel.AppointmentsListViewModel

class ScheduleFragment: Fragment() {
    private lateinit var binding: ActivityStudentScheduleBinding
    private val viewmodel: AppointmentsListViewModel by activityViewModels()

    private val nothingFragment = NoAppointmentsFragment.newInstance()
    private val appointmentsFragment = AppointmentspsychologistFragment.newInstance()
    private val eventsFragment  = EventsFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityStudentScheduleBinding.inflate(inflater, container, false)
        //Firebase.auth.currentUser!!.uid
        viewmodel.downloadAppointments("UjZ9bvrXxCexOXvFV2nF")
        loadAppointments()
        binding.btnAppointment.isEnabled = false
        binding.btnEvents.setOnClickListener {
            binding.btnAppointment.isEnabled = true
            binding.btnAppointment.setTextColor(Color.parseColor("#7802D4"))
            binding.btnEvents.isEnabled = false
            binding.btnEvents.setTextColor(Color.WHITE)
            viewmodel.setType(2)
            viewmodel.downloadEvents()
            loadEvents()

        }
        binding.btnAppointment.setOnClickListener {
            binding.btnEvents.isEnabled = true
            binding.btnEvents.setTextColor(Color.parseColor("#7802D4"))
            binding.btnAppointment.isEnabled = false
            binding.btnAppointment.setTextColor(Color.WHITE)
            viewmodel.setType(1)
            viewmodel.downloadAppointments("UjZ9bvrXxCexOXvFV2nF")
            loadAppointments()
        }

        binding.tablayout.tabGravity = TabLayout.GRAVITY_FILL

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewmodel.setMonth(tab!!.position + 1)
                if(viewmodel.getType() == 1){
                    viewmodel.downloadAppointments("UjZ9bvrXxCexOXvFV2nF")
                    loadAppointments()
                }else{
                    viewmodel.downloadEvents()
                    loadEvents()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return binding.root
    }

    private fun loadEvents(){
        viewmodel.eventsListLiveData.observe(viewLifecycleOwner) { eventList ->
            if (eventList.size > 0) {
                loadFragment(eventsFragment)

            } else {
            }
        }
    }

    private fun loadAppointments(){
        viewmodel.appointmentsListLiveData.observe(viewLifecycleOwner) { appointmentsList ->
            if (appointmentsList.size > 0) {
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
        @JvmStatic
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }
}