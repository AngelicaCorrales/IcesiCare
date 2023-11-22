package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.icesicare.databinding.FragmentStudentChatBinding

class StudentChatFragment : Fragment() {
    //private val viewModel : StudentProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStudentChatBinding = FragmentStudentChatBinding.inflate(inflater, container, false)


        return binding.root
    }

    companion object{
        fun newInstance():StudentChatFragment{
            return StudentChatFragment()
        }
    }
}