package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.ScheduleFragmentBinding

class ScheduleFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ScheduleFragmentBinding= ScheduleFragmentBinding.inflate(inflater,container,false)


        return binding.root
    }

    companion object{
        fun newInstance():ScheduleFragment{
            return  ScheduleFragment()
        }
    }
}