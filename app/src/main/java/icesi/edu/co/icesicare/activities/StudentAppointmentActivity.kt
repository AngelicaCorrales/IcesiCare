package icesi.edu.co.icesicare.activities

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.ActivityStudentAppointmentInfoBinding
import icesi.edu.co.icesicare.viewmodel.StudentViewModel
import java.util.Date

class StudentAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentAppointmentInfoBinding.inflate(layoutInflater)
    }

    val viewModelAppointment : StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Acá, a partir del currentId del estudiante debe llegar el id de la cita a la que le dio clic
        viewModelAppointment.getStudentAppointment("0ShRR6kcEUhAMAchb2Nf")
        viewModelAppointment.psychologistLV.observe(this){
            binding.psychologistName.text = it.name
            binding.psychologistDescription.text = it.description
            binding.psychologistCarrer.text = validateGenre(it.genre)

            if (it.profileImageURL != ""){
                Glide.with(this).load(it.profileImageURL).into(binding.psychologistProfileImg)
            }
        }

        viewModelAppointment.appointmentLV.observe(this){
            binding.appointmentDay.text = formatDay(it.date)
            binding.appointmentHour.text = formatHour(it.date)
            binding.appointmentComment.text = it.motive
        }
    }

    fun validateGenre(genre : String) : String{
        var psychologistGenre = "Psicóloga"

        if (genre == "M"){
            psychologistGenre = "Psicólogo"
        }
        return psychologistGenre
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