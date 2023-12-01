package icesi.edu.co.icesicare.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.StudentMainActivity
import icesi.edu.co.icesicare.databinding.FragmentMakeAppointmentPsychDetailBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.util.CalendarUtils
import icesi.edu.co.icesicare.viewmodel.AppointmentViewModel
import icesi.edu.co.icesicare.viewmodel.ScheduleViewModel

class MakeAppointmentPsychDetailFragment : Fragment() {

    var psych:Psychologist? = null
    lateinit var binding:FragmentMakeAppointmentPsychDetailBinding
    private val scheduleViewModel : ScheduleViewModel by viewModels()
    private val appoinmentViewModel : AppointmentViewModel by viewModels()

    val weeklyCalendarFragment by lazy{
        WeeklyCalendarFragment.newInstance()
    }

    private var buttonHoursList: List<Button> = listOf()
    private var hoursMap: Map<Button,Double> = mapOf()
    var selected_hour: Button? =null
    var textButtonColor: ColorStateList? =null
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
         textButtonColor = ContextCompat.getColorStateList(requireContext(), R.color.text_button_color)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakeAppointmentPsychDetailBinding.inflate(inflater,container,false)
        initializeVals()
        loadPsychDetail()
        loadWeeklyCalendar()

        loadScheduleForDay()

        makeAppointment()

        scheduleViewModel.scheduleLV.observe(viewLifecycleOwner){ sch->
            enableHoursBySchedule(sch?.startHour,sch?.endHour)
        }

        CalendarUtils.selectedDate.observe(viewLifecycleOwner) { newSelectedDate ->
            // Reactualizar el fragmento cuando cambie la fecha seleccionada
            loadScheduleForDay()
        }
        setClicksButtonsHour()

        appoinmentViewModel.appointmentLV.observe(viewLifecycleOwner){

        }

        appoinmentViewModel.errorLV.observe(viewLifecycleOwner){error->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun onButtonClick(button: Button){



        selected_hour?.setBackgroundResource(R.drawable.appointment_button_background_not_selected)
        selected_hour?.setTextColor(textButtonColor)

        button.setBackgroundResource(R.drawable.btn_bg_selected)
        button.setTextColor(getColor(requireContext(),R.color.neutral_white))
        selected_hour=button
       Log.e("<<<<",hoursMap[selected_hour].toString())
    }

    fun setClicksButtonsHour(){
        binding.btn8.setOnClickListener {
            onButtonClick(binding.btn8)
        }
        binding.btn830.setOnClickListener {
            onButtonClick(binding.btn830)
        }
        binding.btn9.setOnClickListener {
            onButtonClick(binding.btn9)
        }
        binding.btn930.setOnClickListener {
            onButtonClick(binding.btn930)
        }
        binding.btn10.setOnClickListener {
            onButtonClick(binding.btn10)
        }
        binding.btn1030.setOnClickListener {
            onButtonClick(binding.btn1030)
        }
        binding.btn11.setOnClickListener {
            onButtonClick(binding.btn11)
        }
        binding.btn1130.setOnClickListener {
            onButtonClick(binding.btn1130)
        }
        binding.btn2.setOnClickListener {
            onButtonClick(binding.btn2)
        }
        binding.btn230.setOnClickListener {
            onButtonClick(binding.btn230)
        }
        binding.btn3.setOnClickListener {
            onButtonClick(binding.btn3)
        }
        binding.btn330.setOnClickListener {
            onButtonClick(binding.btn330)
        }
        binding.btn4.setOnClickListener {
            onButtonClick(binding.btn4)
        }
        binding.btn430.setOnClickListener {
            onButtonClick(binding.btn430)
        }
        binding.btn5.setOnClickListener {
            onButtonClick(binding.btn5)
        }
        binding.btn530.setOnClickListener {
            onButtonClick(binding.btn530)
        }
    }

    fun loadScheduleForDay(){
        psych?.scheduleId?.let {
            scheduleViewModel.getScheduleForDay(it,CalendarUtils.selectedDate.value?.dayOfWeek.toString().lowercase().replaceFirstChar {it.uppercase()})
        }
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

    fun enableHoursBySchedule(startHour:Double?,endHour:Double?){
        selected_hour=null

        if(startHour ==null && endHour==null){
            for (button in buttonHoursList) {

                button.isEnabled = false
                button.setBackgroundResource(R.drawable.appointment_button_background_not_selected)
                button.setTextColor(textButtonColor)

            }
        }else{
            for (button in buttonHoursList) {
                val buttonValue = hoursMap[button] ?: 0.0
                button.isEnabled = buttonValue in startHour!!.. endHour!!-0.1
                button.setBackgroundResource(R.drawable.appointment_button_background_not_selected)
                button.setTextColor(textButtonColor)

            }
        }

    }


    fun makeAppointment(){
        binding.makeAppoBtn.setOnClickListener {
            if (selected_hour != null && !binding.motiveDescTV.text.toString().isEmpty()) {

                hoursMap[selected_hour]?.let {
                    appoinmentViewModel.saveAppointment(
                        CalendarUtils.selectedDate.value!!,
                        it,
                        binding.motiveDescTV.text.toString(),
                        psych!!.id,
                    )

                    showAlertDialog(
                        "¡Solicitud enviada con éxito!",
                        "Le informaremos cuando el psicólogo acepte la cita"
                    )


                }


            } else {
                Toast.makeText(
                    requireContext(),
                    "¡No te olvides de llenar toda la información!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        }


    private fun showAlertDialog(title:String,message:String){
        val builder = AlertDialog.Builder(requireContext()).setTitle(title).setMessage(message) .setNeutralButton("Aceptar") { dialog, _ ->
            val intent = Intent(requireContext(), StudentMainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }
        builder.create().show()

    }

    companion object {
        fun newInstance():MakeAppointmentPsychDetailFragment{
            return MakeAppointmentPsychDetailFragment()
        }
    }
}