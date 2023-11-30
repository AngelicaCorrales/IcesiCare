package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminPsychologistsBinding

class AdminPsychologistsActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAdminPsychologistsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}