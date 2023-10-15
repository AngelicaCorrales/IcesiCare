package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import icesi.edu.co.icesicare.databinding.ActivityStudentAppointmentInfoBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.viewmodel.AppointmentViewModel
import icesi.edu.co.icesicare.viewmodel.PsychologistViewModel

class StudentAppointmentInfo : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentAppointmentInfoBinding.inflate(layoutInflater)
    }

    val viewModelAppointment : AppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Acá, a partir del currentId del estudiante debe llegar el id de la cita a la que le dio clic
        viewModelAppointment.getStudentAppointment("0ShRR6kcEUhAMAchb2Nf")
        viewModelAppointment.psychologistLV.observe(this){
            binding.psychologistName.text = it.name
            binding.psychologistDescription.text = it.description
        }

        viewModelAppointment.appointmentLV.observe(this){
            binding.appointmentDay.text = it.date.date.toString()
            binding.appointmentHour.text = it.date.time.toString()
            binding.appointmentComment.text = it.motive
        }


    }

}