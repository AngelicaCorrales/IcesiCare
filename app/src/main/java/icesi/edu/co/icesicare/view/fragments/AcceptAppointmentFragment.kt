package icesi.edu.co.icesicare.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentAcceptAppointmentBinding
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import icesi.edu.co.icesicare.view.adapters.AcceptApptAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AcceptAppointmentFragment : Fragment() {

    private lateinit var adapter:AcceptApptAdapter
    private lateinit var fragmentActivity:AcceptAppointmentActivity
    private lateinit var psychId: String
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentActivity = activity as AcceptAppointmentActivity

        val binding = FragmentAcceptAppointmentBinding.inflate(inflater,container,false)
        adapter = AcceptApptAdapter(this)
        binding.apptRV.adapter = adapter
        binding.apptRV.layoutManager = LinearLayoutManager(context)
        binding.apptRV.setHasFixedSize(true)

        AppointmentsRepository.apptsLiveData.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
        }

        lifecycleScope.launch (Dispatchers.IO) {
            psychId = Firebase.auth.currentUser?.uid.toString()

            fetchAppointmentsForPsychologist(psychId,false,false)

            withContext(Dispatchers.Main){
                fragmentActivity.changeProgressBarVisibility(false)
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
            fetchAppointmentsForPsychologist(psychId,false,false)

            withContext(Dispatchers.Main){
                fragmentActivity.changeProgressBarVisibility(false)
            }
        }
    }

    companion object {
        fun newInstance():AcceptAppointmentFragment {
            return AcceptAppointmentFragment()
        }

    }
}


