package icesi.edu.co.icesicare.activities

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.databinding.ActivityStudentAppointmentInfoBinding
import icesi.edu.co.icesicare.util.DataDateUtils
import icesi.edu.co.icesicare.viewmodel.StudentViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class StudentAppointmentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentAppointmentInfoBinding.inflate(layoutInflater)
    }

    val viewModelAppointment : StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val appointmentId:String? = intent.extras?.getString("appointmentId")

        viewModelAppointment.getStudentAppointment(appointmentId!!)
        viewModelAppointment.psychologistLV.observe(this){
            binding.psychologistName.text = it.name
            binding.psychologistDescription.text = it.description
            binding.psychologistCarrer.text = validateGenre(it.genre)

            if (it.profileImageURL != ""){
                Glide.with(this).load(it.profileImageURL).into(binding.psychologistProfileImg)
            }
        }

        viewModelAppointment.appointmentLV.observe(this){

            binding.appointmentDay.text = DataDateUtils.formatDay(it.date)
            binding.appointmentHour.text = DataDateUtils.formatHour(it.date)
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




}