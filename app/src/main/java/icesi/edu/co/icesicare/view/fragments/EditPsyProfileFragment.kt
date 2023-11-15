package icesi.edu.co.icesicare.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.concurrent.thread

class EditPsyProfileFragment : Fragment() {

    private lateinit var fragmentActivity: PsyProfileActivity
    private lateinit var binding: FragmentEditpsyprofileBinding
    private val psychologistId = "tdapPPL5ut9eyOzOkIso"
    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>
    private lateinit var newImageURIStr: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        galleryLauncher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)
        fragmentActivity = activity as PsyProfileActivity
        fragmentActivity.showLoading(true)
        binding = FragmentEditpsyprofileBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(psychologistId)
            withContext(Dispatchers.Main) {
                psy?.let {
                    newImageURIStr = binding.psyImgUrl.text.toString()
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

        binding.selectPictureBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
            true

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

    fun onGalleryResult (result: ActivityResult){
        if (result.resultCode == RESULT_OK){
            val uri = result.data?.data
            binding.psyProfileImg.setImageURI(uri)

            newImageURIStr = UUID.randomUUID().toString()
        }
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
            profileImageURL = newImageURIStr,
            scheduleId = binding.psySchId.text.toString()

        )

        lifecycleScope.launch(Dispatchers.IO) {
            PsychRepository.updatePsy(psychologistId, updatedPsy)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireActivity(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
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