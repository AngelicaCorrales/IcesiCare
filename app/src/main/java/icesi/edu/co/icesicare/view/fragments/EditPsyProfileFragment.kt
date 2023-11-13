package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPsyProfileFragment : Fragment() {

    private lateinit var fragmentActivity: PsyProfileActivity
    private lateinit var binding: FragmentEditpsyprofileBinding
    private val psychologistId = "tdapPPL5ut9eyOzOkIso"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentActivity = activity as PsyProfileActivity
        fragmentActivity.showLoading(true)
        binding = FragmentEditpsyprofileBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(psychologistId)
            withContext(Dispatchers.Main) {
                psy?.let {
                    binding.psyId.text = psy.id
                    binding.psyRole.text = psy.role
                    binding.psyImgId.text = psy.profileImageId
                    binding.psyImgUrl.text = psy.profileImageURL
                    binding.psySchId.text = psy.scheduleId
                    binding.psyEmail.text = psy.email.toEditable()
                    binding.psyName.text = psy.name.toEditable()
                    binding.psyDescr.text = psy.description.toEditable()
                    when (psy.genre) {
                        "F" -> binding.radioFemale.isChecked = true
                        "M" -> binding.radioMale.isChecked = true
                        "O" -> binding.radioOther.isChecked = true
                    }

                    psy.profileImageURL?.let { imageUrl ->
                        if (imageUrl.isNotEmpty()) {
                            Glide.with(this@EditPsyProfileFragment).load(imageUrl).into(binding.psyProfileImg)
                        }
                    }
                }
                fragmentActivity.showLoading(false)
            }
        }

        binding.backBtn.setOnClickListener {
            activity?.finish()
        }

        binding.saveChangesBtn.setOnClickListener {
            saveChanges()
            activity?.finish()
        }
        binding.editScheduleBtn.setOnClickListener {
            saveChanges()
            fragmentActivity.showEditPsyScheduleFragment()
        }

        return binding.root
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    private fun saveChanges(){
        val updatedPsy = Psychologist(
            id = binding.psyId.text.toString(),
            role = binding.psyRole.text.toString(),
            email = binding.psyEmail.text.toString(),
            name = binding.psyName.text.toString(),
            genre = if (binding.radioFemale.isChecked) "F" else if (binding.radioMale.isChecked) "M" else "O",
            description = binding.psyDescr.text.toString(),
            profileImageId = binding.psyImgId.text.toString(),
            profileImageURL = binding.psyImgUrl.text.toString(),
            scheduleId = binding.psySchId.text.toString()

            // TODO: save new image
        )

        lifecycleScope.launch(Dispatchers.IO) {
            PsychRepository.updatePsy(psychologistId, updatedPsy)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Changes saved successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): EditPsyProfileFragment {
            return EditPsyProfileFragment()
        }
    }
}