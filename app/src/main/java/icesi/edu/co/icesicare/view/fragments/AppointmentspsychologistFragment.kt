package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.MainActivity
import icesi.edu.co.icesicare.R
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
        return binding.root
    }

    companion object {

        fun newInstance() = AppointmentspsychologistFragment()
    }
}
