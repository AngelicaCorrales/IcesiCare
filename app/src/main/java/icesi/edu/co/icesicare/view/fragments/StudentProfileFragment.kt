package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.StudentProfileFragmentBinding
import icesi.edu.co.icesicare.viewmodel.StudentProfileViewModel

class StudentProfileFragment: Fragment() {

    private val viewModel : StudentProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: StudentProfileFragmentBinding = StudentProfileFragmentBinding.inflate(inflater,container,false)

        viewModel.getStudent("UjZ9bvrXxCexOXvFV2nF")
        viewModel.studentLV.observe(viewLifecycleOwner){
            val name = it.name+" "+it.lastname
            val age = it.age.toString()+" años"

            binding.profileName.text = name
            binding.profileAge.text = age
            binding.profileCareer.text = it.career
            Glide.with(this).load(it.profileImageUrl).into(binding.profileImage)
        }

        return binding.root
    }

    companion object{
        fun newInstance():StudentProfileFragment{
            return StudentProfileFragment()
        }
    }
}