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
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.databinding.ActivityAcceptAppointmentBinding
import icesi.edu.co.icesicare.databinding.FragmentAcceptAppointmentBinding
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.view.adapters.AcceptApptAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AcceptAppointmentFragment : Fragment() {

    private lateinit var adapter:AcceptApptAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentAcceptAppointmentBinding.inflate(inflater,container,false)
        adapter = AcceptApptAdapter(this)
        binding.apptRV.adapter = adapter
        binding.apptRV.layoutManager = LinearLayoutManager(context)
        binding.apptRV.setHasFixedSize(true)

        AppointmentsRepository.apptsLiveData.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
            Log.e("dev","adapter notified")
        }

        lifecycleScope.launch (Dispatchers.IO) {
            fetchAppointmentsForPsychologist("jqpFMbueihpcHNXDlo8V",false,false)
            withContext(Dispatchers.Main){
                //fragmentActivity.changeProgressBarVisibility(false)
            }
        }


        return binding.root
    }

    private suspend fun fetchAppointmentsForPsychologist(psychId: String, isAccepted:Boolean, isCanceled:Boolean){
        AppointmentsRepository.fetchAppointmentsForPsychologist(psychId, isAccepted, isCanceled)
    }

    fun updateAppointment(apptId: String, isAccepted:Boolean, isCanceled:Boolean){
        lifecycleScope.launch (Dispatchers.IO) {
            AppointmentsRepository.updateAppointmentStatus(apptId,isAccepted,isCanceled)
            fetchAppointmentsForPsychologist("jqpFMbueihpcHNXDlo8V",false,false)

            withContext(Dispatchers.Main){
                //fragmentActivity.changeProgressBarVisibility(false)
            }
        }
    }

    companion object {
        fun newInstance():AcceptAppointmentFragment {
            return AcceptAppointmentFragment()
        }

    }
}


