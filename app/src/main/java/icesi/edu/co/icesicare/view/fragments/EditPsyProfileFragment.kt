package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding

class EditPsyProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditpsyprofileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditpsyprofileBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPsyProfileFragment()
    }
}