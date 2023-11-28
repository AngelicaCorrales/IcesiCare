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
    private val dayNameMap = mapOf(
        "Lunes" to "Monday",
        "Martes" to "Tuesday",
        "Miércoles" to "Wednesday",
        "Jueves" to "Thursday",
        "Viernes" to "Friday"
    )
    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_days_item, items)
        binding.daysInput.setAdapter(adapter)
        binding.daysInput.clearListSelection()
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

    private fun updateTimesForSelectedDay(selectedDay: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val psyId = Firebase.auth.currentUser?.uid.orEmpty()
            psy = PsychRepository.fetchOnePsy(psyId)

            if (psy?.scheduleId != null) {

                val dayInEnglish = dayNameMap[selectedDay] ?: return@launch

                try {
                    val daySchedule = ScheduleRepository.getScheduleForDay(psy!!.scheduleId, dayInEnglish)

                    withContext(Dispatchers.Main) {
                        val startHourDecimal = daySchedule?.startHour?.toDoubleOrNull()
                        val endHourDecimal = daySchedule?.endHour?.toDoubleOrNull()

                        val formattedStartHour = convertDecimalToTime(startHourDecimal)
                        val formattedEndHour = convertDecimalToTime(endHourDecimal)

                        binding.fromTimeTextView.text = formattedStartHour
                        binding.toTimeTextView.text = formattedEndHour
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("EditPsyScheduleFragment", "Error fetching schedule", e)
                        binding.fromTimeTextView.text = "--:--"
                        binding.toTimeTextView.text = "--:--"
                    }
                }
            } else {
                Log.e("EditPsyScheduleFragment", "Psychologist or Schedule ID is null")
            }
        }
    }

    private fun isValidTimeFormat(time: String?): String {
        val regex = Regex("(\\d{2}:\\d{2})|--:--")
        return if (time != null && regex.matches(time)) time else "--:--"
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

    private fun saveSchedule() {
        lifecycleScope.launch(Dispatchers.IO) {
            val psyId = Firebase.auth.currentUser?.uid.orEmpty()

            val selectedDay = withContext(Dispatchers.Main) {
                binding.daysInput.text.toString()
            }

            val dayInEnglish = dayNameMap[selectedDay] ?: return@launch

            val startTime = withContext(Dispatchers.Main) {
                binding.fromTimeTextView.text.toString()
            }
            val endTime = withContext(Dispatchers.Main) {
                binding.toTimeTextView.text.toString()
            }

            val validStartTime = isValidTimeFormat(startTime)
            val validEndTime = isValidTimeFormat(endTime)

            if (validStartTime != "--:--" && validEndTime != "--:--") {
                try {
                    val psy = PsychRepository.fetchOnePsy(psyId)
                    val startTimeDecimal = convertTimeToDecimal(validStartTime).toString()
                    val endTimeDecimal = convertTimeToDecimal(validEndTime).toString()
                    ScheduleRepository.updateScheduleForDay(psy!!.scheduleId, dayInEnglish, startTimeDecimal, endTimeDecimal)
                    Log.i("EditPsyScheduleFragment", "Schedule updated successfully")
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("EditPsyScheduleFragment", "Error updating schedule", e)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.e("EditPsyScheduleFragment", "Invalid time format")
                }
            }
        }
    }

    private fun convertTimeToDecimal(time: String): Double {
        val parts = time.split(":").map { it.toIntOrNull() ?: 0 }
        val hours = parts[0]
        val minutes = parts[1]

        val minuteFraction = minutes / 60.0
        return hours + minuteFraction
    }

    private fun convertDecimalToTime(decimalTime: Double?): String {
        if (decimalTime == null) {
            return "--:--"
        }
        val hours = decimalTime.toInt()
        val minutes = ((decimalTime - hours) * 60).toInt()
        return String.format("%02d:%02d", hours, minutes)
    }
    companion object {
        @JvmStatic
        fun newInstance(): EditPsyScheduleFragment {
            return EditPsyScheduleFragment()
        }
    }
}