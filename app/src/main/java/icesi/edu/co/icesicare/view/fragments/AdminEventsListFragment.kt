package icesi.edu.co.icesicare.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AdminEventsActivity
import icesi.edu.co.icesicare.databinding.FragmentAdminEventsListBinding
import icesi.edu.co.icesicare.view.adapters.AdminEventAdapter
import icesi.edu.co.icesicare.viewmodel.EventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminEventsListFragment : Fragment() {

    companion object {
        fun newInstance() = AdminEventsListFragment()
    }

    private val viewModel: EventViewModel by viewModels()
    private lateinit var binding: FragmentAdminEventsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentAdminEventsListBinding.inflate(inflater, container, false)

        val eventAdapter = AdminEventAdapter(activity as AdminEventsActivity)

        viewModel.eventsLD.observe(viewLifecycleOwner){

            Log.e("DEV",it.toString())
            eventAdapter.events = it
        }

        lifecycleScope.launch (Dispatchers.IO){
            viewModel.getAllEvents()
        }

        binding.addEventBtn.setOnClickListener{
            (activity as AdminEventsActivity).showAddEventFragment()
        }

        binding.eventsRV.adapter = eventAdapter
        binding.eventsRV.layoutManager = LinearLayoutManager(context)
        binding.eventsRV.setHasFixedSize(true)

        Log.e("DEV","Called onCreateView from AdminEventsListFragment")

        return binding.root
    }
}