package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
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
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditPsyScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPsyScheduleFragment  : Fragment() {
    private lateinit var fragmentActivity: PsyProfileActivity
    private var _binding: FragmentEditPsyScheduleBinding? = null
    private val binding get() = _binding!!

    private val items = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_days_item, items)
        binding.daysInput.setAdapter(adapter)
        binding.daysInput.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedDay = adapterView.getItemAtPosition(position).toString()
            binding.fromTimeTextView.text = selectedDay
            binding.toTimeTextView.text = selectedDay
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

    private fun fromOpenTimePicker(){
        openTimePicker()

    }
    private fun toOpenTimePicker(){
        openTimePicker()

    }
    private fun openTimePicker(){
        val isSys24hFormat = is24HourFormat(requireContext())
        val clockFormat = if(isSys24hFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecciona tu horario")
            .build()
        picker.show(childFragmentManager, "TAG")
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