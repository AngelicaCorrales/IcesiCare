package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
class AdminEventsListFragment : Fragment() {

    companion object {
        fun newInstance() = AdminEventsListFragment()
    }

    private val viewModel: EventViewModel by viewModels()
    private lateinit var binding: FragmentAdminEventsListBinding
    private lateinit var parentActivity: AdminEventsActivity
    private lateinit var eventAdapter: AdminEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentAdminEventsListBinding.inflate(inflater, container, false)
        parentActivity = activity as AdminEventsActivity
        eventAdapter = AdminEventAdapter(parentActivity)

        viewModel.eventsLD.observe(viewLifecycleOwner){
            eventAdapter.events = it
            binding.progressBarAdmEvent.visibility = View.GONE
        }

        lifecycleScope.launch (Dispatchers.IO){
            binding.progressBarAdmEvent.visibility = View.VISIBLE
            viewModel.getAllEvents()
        }

        binding.addEventBtn.setOnClickListener{
            (activity as AdminEventsActivity).showAddEventFragment()
        }

        val colorNeutralWhite = this.context?.let { it1 -> ContextCompat.getColor(it1, R.color.neutral_white) }
        val colorPurple = this.context?.let { it1 -> ContextCompat.getColor(it1, R.color.purple) }

        binding.filterAllBtn.setOnClickListener{
            clearFilter()

            binding.filterAllBtn.setBackgroundResource(R.drawable.btn_bg_selected)
            binding.filterAllBtn.setTextColor(colorNeutralWhite!!)
            binding.filterInactiveBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterInactiveBtn.setTextColor(colorPurple!!)
            binding.filterActiveBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterActiveBtn.setTextColor(colorPurple)
        }

        binding.filterActiveBtn.setOnClickListener{
            filterEventsByActive(true)

            binding.filterAllBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterAllBtn.setTextColor(colorPurple!!)
            binding.filterInactiveBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterInactiveBtn.setTextColor(colorPurple)
            binding.filterActiveBtn.setBackgroundResource(R.drawable.btn_bg_selected)
            binding.filterActiveBtn.setTextColor(colorNeutralWhite!!)
        }

        binding.filterInactiveBtn.setOnClickListener{
            filterEventsByActive(false)

            binding.filterAllBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterAllBtn.setTextColor(colorPurple!!)
            binding.filterInactiveBtn.setBackgroundResource(R.drawable.btn_bg_selected)
            binding.filterInactiveBtn.setTextColor(colorNeutralWhite!!)
            binding.filterActiveBtn.setBackgroundResource(R.drawable.btn_bg_unselected)
            binding.filterActiveBtn.setTextColor(colorPurple)
        }

        binding.eventsRV.adapter = eventAdapter
        binding.eventsRV.layoutManager = LinearLayoutManager(context)
        binding.eventsRV.setHasFixedSize(true)

        return binding.root
    }

    fun clearFilter() {
        viewModel.filteredEventsLD.removeObservers(viewLifecycleOwner)
        eventAdapter.events = viewModel.eventsLD.value ?: arrayListOf() //if null puts arrayListOf el viewModel.eventsLD.value
        viewModel.clearFilter()
    }

    fun filterEventsByName(name: String) {
        viewModel.filteredEventsLD.observe(viewLifecycleOwner){
            if (it != null) {
                eventAdapter.events = it
            }
            binding.progressBarAdmEvent.visibility = View.GONE
        }

        binding.progressBarAdmEvent.visibility = View.VISIBLE
        viewModel.filterEventsByName(name)
    }

    fun filterEventsByActive(isActive:Boolean){
        viewModel.filteredEventsLD.observe(viewLifecycleOwner){
            if (it != null) {
                eventAdapter.events = it
            }
            binding.progressBarAdmEvent.visibility = View.GONE
        }

        binding.progressBarAdmEvent.visibility = View.VISIBLE
        viewModel.filterEventsByActive(isActive)
    }

}