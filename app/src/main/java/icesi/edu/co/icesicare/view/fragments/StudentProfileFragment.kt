package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.databinding.FragmentHomeStudBinding
import icesi.edu.co.icesicare.databinding.StudentProfileFragmentBinding
import icesi.edu.co.icesicare.viewmodel.StudentProfileViewModel

class StudentProfileFragment: Fragment() {

    private val viewModel : StudentProfileViewModel by viewModels()
    private lateinit var binding: StudentProfileFragmentBinding

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

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance():StudentProfileFragment{
            return StudentProfileFragment()
        }
    }
}