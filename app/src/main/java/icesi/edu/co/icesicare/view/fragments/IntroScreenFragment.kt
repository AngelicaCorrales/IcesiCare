package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.activities.StudentMainActivity
import icesi.edu.co.icesicare.databinding.IntroductionView1Binding

class IntroScreenFragment : Fragment (){

    lateinit var binding: IntroductionView1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroductionView1Binding.inflate (inflater, container, false)


    binding.comenzarBtn.setOnClickListener {
        val intent= Intent(activity, StudentMainActivity::class.java)
        startActivity(intent)
    }
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = FirstScreenFragment()
    }
}

