package icesi.edu.co.icesicare.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityPsychologistAppointmentBinding
import icesi.edu.co.icesicare.viewmodel.PsycologistApointmentViewModel
import java.text.SimpleDateFormat
import java.util.Date

class PsychologistAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPsychologistAppointmentBinding.inflate(layoutInflater)
    }

    val viewModel : PsycologistApointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Aquí toca hacer que dependiendo de la cita que seleccione, le manda el id de esa cita
        viewModel.getPsychologistAppointment("lPY2PGqUen9vGss6s5dF")
        viewModel.appointmentLV.observe(this){
            binding.appointmentComment.text = it.motive
            binding.appointmentDay.text = formatDay(it.date)
            binding.appointmentHour.text = formatHour(it.date)
        }

        viewModel.studentLV.observe(this){
            val completeName = it.name+" "+it.lastname+""
            val studentInfo = "Edad: "+it.age +"\nCarrera: "+it.carreer+"\nCódigo: "+ it.code

            binding.studentName.text = completeName
            binding.studentGenre.text = validateGenre(it.genre)
            binding.studentInfo.text = studentInfo
            Glide.with(this).load(it.profileImage).into(binding.studentImageProfile)
        }
    }

    fun validateGenre(genre : String) : String{
        var studentGenre = genre

        if (genre == "M"){
            studentGenre = "Hombre"

        }else if (genre == "F"){
            studentGenre = "Mujer"
        }
        return studentGenre
    }

    @SuppressLint("SimpleDateFormat")
    fun formatHour(date : Date) : String{
        val formatHour = SimpleDateFormat("HH:mm:ss")

        return formatHour.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDay(date : Date) : String{
        date.hours = date.hours - 5
        val formatDay = SimpleDateFormat("dd/MM/yyyy")

        return formatDay.format(date)
    }
}