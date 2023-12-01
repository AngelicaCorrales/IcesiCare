package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.databinding.StudentProfileFragmentBinding
import icesi.edu.co.icesicare.viewmodel.StudentProfileViewModel

class StudentProfileFragment: Fragment() {

    private val viewModel : StudentProfileViewModel by viewModels()
    private lateinit var binding: StudentProfileFragmentBinding
    private val launcher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StudentProfileFragmentBinding.inflate(inflater,container,false)

        viewModel.getStudent(Firebase.auth.currentUser?.uid.toString())
        viewModel.studentLV.observe(viewLifecycleOwner){
            val name = it.name+" "+it.lastname
            val age = it.age.toString()+" años"
            val role = "Estudiante"

            if (it.role == "student"){
                binding.profileRole.text = role
            }

            binding.profileName.text = name
            binding.profileAge.text = age
            binding.profileCareer.text = it.career

            if(it.profileImageURL != ""){
                Glide.with(this).load(it.profileImageURL).into(binding.profileImage)
            }
        }

        binding.logoutBtn.setOnClickListener {
            val activity = requireActivity()
            activity.finish()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            Firebase.auth.signOut()
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launcher.launch(intent)
        }

        return binding.root
    }

    private fun onGalleryResult(result: ActivityResult){
        val uri = result.data?.data
        Glide.with(this).load(uri).into(binding.profileImage)

        uri?.let {
            viewModel.uploadImage(it, Firebase.auth.currentUser!!.uid)
        }
    }

    companion object{
        @JvmStatic
        fun newInstance():StudentProfileFragment{
            return StudentProfileFragment()
        }
    }
}