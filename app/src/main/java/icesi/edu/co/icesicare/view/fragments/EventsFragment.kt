package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.databinding.FragmentAppointmentspsychologistBinding
import icesi.edu.co.icesicare.databinding.FragmentViewEventsBinding
import icesi.edu.co.icesicare.view.adapters.AppointmentsAdapter
import icesi.edu.co.icesicare.view.adapters.EventsAdapter
import icesi.edu.co.icesicare.viewmodel.AppointmentsListViewModel

class EventsFragment : Fragment() {
    val viewmodel: AppointmentsListViewModel by activityViewModels()
    val adapter = EventsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewEventsBinding.inflate(inflater, container, false)
        binding.eventList.adapter = adapter
        binding.eventList.layoutManager = LinearLayoutManager(requireContext())
        binding.eventList.setHasFixedSize(true)
        viewmodel.eventsListLiveData.observe(viewLifecycleOwner) { eventList ->
            adapter.addEventsList(eventList)
        }
        return binding.root
    }
    companion object {

        fun newInstance() = EventsFragment()
    }
}