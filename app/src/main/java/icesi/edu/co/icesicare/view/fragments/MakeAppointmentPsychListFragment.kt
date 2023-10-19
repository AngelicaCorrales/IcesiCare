package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentMakeAppointmentPsychListBinding
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.view.adapters.PsychAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MakeAppointmentPsychListFragment : Fragment() {

    private lateinit var adapter:PsychAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = PsychAdapter(activity as MakeAppointmentActivity)

        val binding = FragmentMakeAppointmentPsychListBinding.inflate(inflater,container,false)
        binding.psychListRV.adapter = adapter
        binding.psychListRV.layoutManager = LinearLayoutManager(context)
        binding.psychListRV.setHasFixedSize(true)



        PsychRepository.psychsLiveData.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
            Log.e("dev","adapter notified")
        }

        lifecycleScope.launch (Dispatchers.IO){
            PsychRepository.fetchAllPsychs()
        }

        return binding.root
    }

    companion object {
        fun newInstance():MakeAppointmentPsychListFragment{
           return MakeAppointmentPsychListFragment()
       }
    }
}