package icesi.edu.co.icesicare.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminPsychologistsBinding
import icesi.edu.co.icesicare.view.adapters.AdminPsychAdapter
import icesi.edu.co.icesicare.viewmodel.PsychologistViewModel
import kotlinx.coroutines.launch

class AdminPsychologistsActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAdminPsychologistsBinding.inflate(layoutInflater)
    }

    private val psychViewModel : PsychologistViewModel by viewModels()
    private var colorPurple : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        colorPurple = ContextCompat.getColor(this, R.color.purple)

        val adapter = AdminPsychAdapter(this)
        binding.admPsychRV.adapter = adapter
        binding.admPsychRV.layoutManager = LinearLayoutManager(this)
        binding.admPsychRV.setHasFixedSize(true)

        psychViewModel.psychsPendingApprovalLD.observe(this){
            adapter.psychs = it
        }

        psychViewModel.errorLD.observe(this){ e ->
            e.message?.let { showAlertDialog(it) }
        }

        psychViewModel.getPsychologistsPendingForApproval()

    }

    fun updatePsychStatus(isAccepted: Boolean, psychId: String) {
        psychViewModel.updatePsychStatus(isAccepted,psychId)
    }

    private fun showAlertDialog(message:String){
        val builder = AlertDialog.Builder(this).setTitle(message).setNeutralButton("OK",null)
        builder.create().show()
    }

}