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
import kotlinx.coroutines.withContext

class MakeAppointmentPsychListFragment : Fragment() {

    private lateinit var adapter:PsychAdapter
    private lateinit var fragmentActivity: MakeAppointmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentActivity = activity as MakeAppointmentActivity
        adapter = PsychAdapter(fragmentActivity)

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
            withContext(Dispatchers.Main){
                fragmentActivity.changeProgressBarVisibility(false)
            }

        }

        return binding.root
    }

    companion object {
        fun newInstance():MakeAppointmentPsychListFragment{
           return MakeAppointmentPsychListFragment()
       }
    }
}