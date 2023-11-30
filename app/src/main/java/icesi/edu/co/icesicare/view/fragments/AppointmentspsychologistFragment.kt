package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentAppointmentspsychologistBinding
import icesi.edu.co.icesicare.view.adapters.AppointmentsAdapter
import icesi.edu.co.icesicare.viewmodel.AppointmentsListViewModel


class AppointmentspsychologistFragment : Fragment() {
     val viewmodel: AppointmentsListViewModel by activityViewModels()
     val adapter = AppointmentsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAppointmentspsychologistBinding.inflate(inflater, container, false)
        binding.appointmentList.adapter = adapter
        binding.appointmentList.layoutManager = LinearLayoutManager(requireContext())
        binding.appointmentList.setHasFixedSize(true)
        viewmodel.appointmentsListLiveData.observe(viewLifecycleOwner) { appointmentsList ->
            adapter.addAppoinmentsList(appointmentsList)
       }

        if(adapter.psychologistId != ""){
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

        fun newInstance() = AppointmentspsychologistFragment()
    }
}
