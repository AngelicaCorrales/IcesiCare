package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.FragmentMakeAppointmentPsychDetailBinding
import icesi.edu.co.icesicare.model.entity.Psychologist

class MakeAppointmentPsychDetailFragment : Fragment() {

    var psych:Psychologist? = null
    lateinit var binding:FragmentMakeAppointmentPsychDetailBinding

    val weeklyCalendarFragment by lazy{
        WeeklyCalendarFragment.newInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakeAppointmentPsychDetailBinding.inflate(inflater,container,false)
        loadPsychDetail()
        loadWeeklyCalendar()

        return binding.root
    }

    fun loadPsychDetail(){
        binding.namePsycTV.text = psych?.name
        binding.descriptionPsycTV.text = psych?.description

       if(psych?.profileImageURL != ""){
            Glide.with(this).load(psych?.profileImageURL).into(binding.imagePsycIV)
        }

    }

    fun loadWeeklyCalendar() {
        childFragmentManager.beginTransaction()
            .replace(binding.weeklyCalendar.id,weeklyCalendarFragment).commit()
    }
    companion object {
        fun newInstance():MakeAppointmentPsychDetailFragment{
            return MakeAppointmentPsychDetailFragment()
        }
    }
}