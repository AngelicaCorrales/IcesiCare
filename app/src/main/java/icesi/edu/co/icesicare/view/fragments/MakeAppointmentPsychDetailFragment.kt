package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.FragmentMakeAppointmentPsychDetailBinding
import icesi.edu.co.icesicare.model.entity.Psychologist

class MakeAppointmentPsychDetailFragment : Fragment() {

    var psych:Psychologist? = null
    lateinit var binding:FragmentMakeAppointmentPsychDetailBinding

    val weeklyCalendarFragment by lazy{
        WeeklyCalendarFragment.newInstance()
    }

    private var buttonHoursList: List<Button> = listOf()
    private var hoursMap: Map<Button,Double> = mapOf()
    fun initializeVals(){
        buttonHoursList= listOf(
        binding.btn8,
        binding.btn830,
        binding.btn9,
        binding.btn930,
        binding.btn10,
        binding.btn1030,
        binding.btn11,
        binding.btn1130,
        binding.btn2,
        binding.btn230,
        binding.btn3,
        binding.btn330,
        binding.btn4,
        binding.btn430,
        binding.btn5,
        binding.btn530,
        )

        hoursMap= mapOf(
        binding.btn8 to 8.0,
        binding.btn830 to 8.5,
        binding.btn9 to 9.0,
        binding.btn930 to 9.5,
        binding.btn10 to 10.0,
        binding.btn1030 to 10.5,
        binding.btn11 to 11.0,
        binding.btn1130 to 11.5,
        binding.btn2 to 14.0,
        binding.btn230 to 14.5,
        binding.btn3 to 15.0,
        binding.btn330 to 15.5,
        binding.btn4 to 16.0,
        binding.btn430 to 16.5,
        binding.btn5 to 17.0,
        binding.btn530 to 17.5,
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakeAppointmentPsychDetailBinding.inflate(inflater,container,false)
        initializeVals()
        loadPsychDetail()
        loadWeeklyCalendar()
        enableHoursBySchedule()
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

    fun enableHoursBySchedule(){

        for (button in buttonHoursList) {
            val buttonValue = hoursMap[button] ?: 0.0

            button.isEnabled = buttonValue in 7.0..20.0
        }
    }


    fun makeAppointment(){

    }
    companion object {
        fun newInstance():MakeAppointmentPsychDetailFragment{
            return MakeAppointmentPsychDetailFragment()
        }
    }
}