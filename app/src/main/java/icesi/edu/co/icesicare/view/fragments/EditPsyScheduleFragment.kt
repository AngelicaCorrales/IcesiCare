package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditPsyScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPsyScheduleFragment  : Fragment() {
    private lateinit var fragmentActivity: PsyProfileActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentActivity = activity as PsyProfileActivity
        fragmentActivity.showLoading(true)
        val binding: FragmentEditPsyScheduleBinding = FragmentEditPsyScheduleBinding.inflate(inflater,container,false)

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