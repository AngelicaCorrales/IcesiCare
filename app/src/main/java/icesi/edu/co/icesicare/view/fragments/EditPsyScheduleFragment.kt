package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

    private val items = listOf("Lunes", "Martes", "Miércoles", "Jueves")
    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_days_item, items)
        binding.daysInput.setAdapter(adapter)
        binding.daysInput.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedDay = adapterView.getItemAtPosition(position).toString()
            binding.textToShow.text = selectedDay
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

        binding.backBtn.setOnClickListener {
            fragmentActivity.showEditProfileFragment()
        }

        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(): EditPsyScheduleFragment {
            return EditPsyScheduleFragment()
        }
    }
}