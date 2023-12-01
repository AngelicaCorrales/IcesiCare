package icesi.edu.co.icesicare.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

class EditPsyProfileFragment : Fragment() {

    private lateinit var fragmentActivity: PsyProfileActivity
    private lateinit var binding: FragmentEditpsyprofileBinding
    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>
    private lateinit var newImageURIStr: String
    private lateinit var imgUrl: Uri
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }
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
            val psy = PsychRepository.fetchOnePsy(Firebase.auth.currentUser?.uid.toString())
            withContext(Dispatchers.Main) {
                psy?.let {
                    newImageURIStr = psy.profileImageId.toString()
                    binding.psyId.text = psy.id
                    binding.psyRole.text = psy.role
                    binding.psyImgId.text = psy.profileImageId
                    binding.psyImgUrl.text = psy.profileImageURL
                    binding.psySchId.text = psy.scheduleId
                    binding.psyEmail.text = psy.email.toEditable()
                    binding.psyName.text = psy.name.toEditable()
                    binding.psyDescr.text = psy.description.toEditable()
                    when (psy.genre) {
                        "Femenino" -> binding.radioFemale.isChecked = true
                        "Masculino" -> binding.radioMale.isChecked = true
                        "Otro" -> binding.radioOther.isChecked = true
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

        binding.selectPictureFromGalleryBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
            true

        }

        binding.selectPictureFromCamBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            } else {
                launchCamera()
            }
        }

        binding.saveChangesBtn.setOnClickListener {
            lifecycleScope.launch {
                saveChanges()
                activity?.finish()
            }
        }

        binding.editScheduleBtn.setOnClickListener {
            lifecycleScope.launch {
                saveChanges()
            }
            fragmentActivity.showEditPsyScheduleFragment()
        }

        return binding.root
    }

    private fun createImgFileUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "/Android/data/icesi.edu.co.icesicare/files"
        )
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val image = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )

        return FileProvider.getUriForFile(
            requireContext(),
            "icesi.edu.co.icesicare",
            image
        )
    }

    private fun launchCamera() {
        imgUrl = createImgFileUri()
        cameraLauncher.launch(imgUrl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                newImageURIStr = UUID.randomUUID().toString()
                val storageRef = Firebase.storage.reference.child("users").child("profileImages").child(newImageURIStr)
                storageRef.putFile(imgUrl).addOnSuccessListener {
                    Glide.with(this@EditPsyProfileFragment).load(imgUrl).into(binding.psyProfileImg)
                }
            }
        }
    }
    fun onGalleryResult (result: ActivityResult){
        if (result.resultCode == RESULT_OK){
            val uri = result.data?.data
            binding.psyProfileImg.setImageURI(uri)

            newImageURIStr = UUID.randomUUID().toString()
            Firebase.storage.reference.child("users").child("profileImages").child(newImageURIStr).putFile(uri!!)
        }
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    private suspend fun saveChanges(){
        val updatedPsy = Psychologist(
            id = binding.psyId.text.toString(),
            role = binding.psyRole.text.toString(),
            email = binding.psyEmail.text.toString(),
            name = binding.psyName.text.toString(),
            genre = if (binding.radioFemale.isChecked) "Femenino" else if (binding.radioMale.isChecked) "Masculino" else "Otro",
            description = binding.psyDescr.text.toString(),
            profileImageId = newImageURIStr,
            profileImageURL = newImageURIStr,
            scheduleId = binding.psySchId.text.toString(),
            approved = true,
            pendingApproval = false

        )

        PsychRepository.updatePsy(Firebase.auth.currentUser?.uid.toString(), updatedPsy)
        withContext(Dispatchers.Main) {
            Toast.makeText(requireActivity(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
        }

    }
    companion object {
        private const val CAMERA_REQUEST_CODE = 100
        @JvmStatic
        fun newInstance(): EditPsyProfileFragment {
            return EditPsyProfileFragment()
        }
    }

}