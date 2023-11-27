package icesi.edu.co.icesicare.view.fragments

import android.app.AlertDialog
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
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.view.adapters.AdminEventAdapter
import icesi.edu.co.icesicare.viewmodel.EventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class AdminEventsListFragment : Fragment() {

    companion object {
        fun newInstance() = AdminEventsListFragment()
    }

    private enum class Filter {
        ALL, ACTIVE, INACTIVE
    }

    private val viewModel: EventViewModel by viewModels()
    private lateinit var binding: FragmentAdminEventsListBinding
    private lateinit var parentActivity: AdminEventsActivity
    private lateinit var eventAdapter: AdminEventAdapter
    private var colorNeutralWhite : Int = 0
    private var colorPurple : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentAdminEventsListBinding.inflate(inflater, container, false)
        parentActivity = activity as AdminEventsActivity
        eventAdapter = AdminEventAdapter(parentActivity,this)

        colorNeutralWhite = ContextCompat.getColor(this.requireContext(), R.color.neutral_white)
        colorPurple = ContextCompat.getColor(this.requireContext(), R.color.purple)


        viewModel.eventsLD.observe(viewLifecycleOwner){
            eventAdapter.events = it.toMutableList()
            binding.progressBarAdmEvent.visibility = View.GONE
        }

        lifecycleScope.launch (Dispatchers.IO){
            binding.progressBarAdmEvent.visibility = View.VISIBLE
            viewModel.getAllEvents()
        }

        binding.addEventBtn.setOnClickListener{
            (activity as AdminEventsActivity).showAddEventFragment()
        }

        binding.filterAllBtn.setOnClickListener{
            binding.progressBarAdmEvent.visibility = View.VISIBLE
            clearFilter()
            switchFilterButtonColors(Filter.ALL)
        }

        binding.filterActiveBtn.setOnClickListener{
            binding.progressBarAdmEvent.visibility = View.VISIBLE
            filterEventsByActive(true)
            switchFilterButtonColors(Filter.ACTIVE)
        }

        binding.filterInactiveBtn.setOnClickListener{
            binding.progressBarAdmEvent.visibility = View.VISIBLE
            filterEventsByActive(false)
            switchFilterButtonColors(Filter.INACTIVE)
        }

        binding.eventsRV.adapter = eventAdapter
        binding.eventsRV.layoutManager = LinearLayoutManager(context)
        binding.eventsRV.setHasFixedSize(true)

        return binding.root
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

    /**
     * Filter events by isActive. An event is active if its date is after current date.
     */
    private fun filterEventsByActive(isActive:Boolean){
        viewModel.filteredEventsLD.observe(viewLifecycleOwner){
            if (it != null) {
                eventAdapter.events = it
            }
            binding.progressBarAdmEvent.visibility = View.GONE
        }

        binding.progressBarAdmEvent.visibility = View.VISIBLE
        viewModel.filterEventsByActive(isActive)
    }

    fun clearFilter() {
        viewModel.filteredEventsLD.removeObservers(viewLifecycleOwner)
        eventAdapter.events = viewModel.eventsLD.value ?: arrayListOf()
        viewModel.clearFilter()
    }

    private fun switchFilterButtonColors(filter:Filter){

        binding.filterAllBtn.setBackgroundResource(
            if (filter == Filter.ALL) R.drawable.btn_bg_selected
            else R.drawable.btn_bg_unselected)
        binding.filterAllBtn.setTextColor(
            if (filter == Filter.ALL) colorNeutralWhite
            else colorPurple)

        binding.filterInactiveBtn.setBackgroundResource(
            if (filter == Filter.INACTIVE) R.drawable.btn_bg_selected
            else R.drawable.btn_bg_unselected)
        binding.filterInactiveBtn.setTextColor(
            if (filter == Filter.INACTIVE) colorNeutralWhite
            else colorPurple)

        binding.filterActiveBtn.setBackgroundResource(
            if (filter == Filter.ACTIVE) R.drawable.btn_bg_selected
            else R.drawable.btn_bg_unselected)
        binding.filterActiveBtn.setTextColor(
            if (filter == Filter.ACTIVE) colorNeutralWhite
            else colorPurple)
    }

    fun promptDeleteConfirmation(event:Event) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
        builder
            .setTitle("¿Está seguro que desea eliminar el evento: ${event.name}?")
            .setPositiveButton("Sí") { dialog, which ->
                viewModel.deleteEvent(event)
            }
            .setNegativeButton("No") { dialog, which ->
                // Do nothing
            }

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            val posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setTextColor(colorNeutralWhite)
            posButton.setBackgroundColor(colorPurple)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPurple)
        }
        dialog.show()
    }




}