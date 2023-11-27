package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditPsyScheduleBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Schedule
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.model.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPsyScheduleFragment  : Fragment() {
    private lateinit var fragmentActivity: PsyProfileActivity
    private var _binding: FragmentEditPsyScheduleBinding? = null
    private val binding get() = _binding!!
    private var schedule: Schedule? = null
    private var psy: Psychologist? = null
    private val items = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_days_item, items)
        binding.daysInput.setAdapter(adapter)
        binding.daysInput.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedDay = adapterView.getItemAtPosition(position).toString()
            updateTimesForSelectedDay(selectedDay)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentActivity = activity as PsyProfileActivity
        fragmentActivity.showLoading(true)
        _binding = FragmentEditPsyScheduleBinding.inflate(inflater,container,false)

        binding.saveScheduleChangesBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireActivity(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
                }
            }
            fragmentActivity.showEditProfileFragment()
        }

        loadSchedule(Firebase.auth.currentUser?.uid.toString())


        fragmentActivity.showLoading(false)

        binding.editFromTimeBtn.setOnClickListener {
            fromOpenTimePicker()
        }

        binding.editToTimeBtn.setOnClickListener {
            toOpenTimePicker()
        }

        binding.saveScheduleChangesBtn.setOnClickListener {
            saveSchedule()
        }

        binding.backBtn.setOnClickListener {
            fragmentActivity.showEditProfileFragment()
        }

        return binding.root
    }

    private fun loadSchedule(psychologistId: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            psy = PsychRepository.fetchOnePsy(psychologistId)

            if (psy != null && psy?.scheduleId != null) {
                schedule = ScheduleRepository.getSchedule(psy!!.scheduleId)
            } else {
                Log.e("EditPsyScheduleFragment", "Psychologist or Schedule ID is null")
            }

            withContext(Dispatchers.Main) {

            }
        }
    }

    private fun updateTimesForSelectedDay(selectedDay: String) {

    }

    private fun fromOpenTimePicker() {
        openTimePicker { hour, minute ->
            binding.fromTimeTextView.text = formatTime(hour, minute)
        }
    }

    private fun toOpenTimePicker() {
        openTimePicker { hour, minute ->
            binding.toTimeTextView.text = formatTime(hour, minute)
        }
    }

    private fun openTimePicker(onTimeSelected: (Int, Int) -> Unit) {
        val isSys24hFormat = is24HourFormat(requireContext())
        val clockFormat = if(isSys24hFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecciona tu horario")
            .build()

        picker.addOnPositiveButtonClickListener {
            onTimeSelected(picker.hour, picker.minute)
        }

        picker.show(childFragmentManager, "TAG")
    }

    private fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }

    private fun saveSchedule(){

    }

    companion object {
        @JvmStatic
        fun newInstance(): EditPsyScheduleFragment {
            return EditPsyScheduleFragment()
        }
    }
}