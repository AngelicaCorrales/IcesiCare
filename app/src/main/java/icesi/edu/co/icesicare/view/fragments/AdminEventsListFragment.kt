package icesi.edu.co.icesicare.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AdminEventsActivity
import icesi.edu.co.icesicare.databinding.FragmentAdminEventsListBinding
import icesi.edu.co.icesicare.viewmodel.EventsViewModel

class AdminEventsListFragment : Fragment() {

    companion object {
        fun newInstance() = AdminEventsListFragment()
    }

    private lateinit var viewModel: EventsViewModel
    private lateinit var binding: FragmentAdminEventsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        viewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        binding = FragmentAdminEventsListBinding.inflate(inflater, container, false)

        binding.addEventBtn.setOnClickListener{
            (activity as AdminEventsActivity).showAddEventFragment()
        }

        return binding.root
    }
}