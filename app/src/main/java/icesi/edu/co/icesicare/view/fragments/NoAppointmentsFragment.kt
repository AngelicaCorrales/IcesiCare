package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R

/**
 * A simple [Fragment] subclass.
 * Use the [NoAppointmentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoAppointmentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_appointments, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoAppointmentsFragment()
    }
}
