package icesi.edu.co.icesicare.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminPsychologistsBinding
import icesi.edu.co.icesicare.model.entity.Event
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

        binding.admPsychBackBtn.setOnClickListener{
            startActivity(Intent(this,AdminMainActivity::class.java))
            this.finish()
        }

        psychViewModel.psychsPendingApprovalLD.observe(this){
            if(it.isEmpty()) binding.admPsychNoTV.visibility = View.VISIBLE
            else binding.admPsychNoTV.visibility = View.GONE

            adapter.psychs = it
            binding.progressBarAdmPsych.visibility = View.GONE
        }

        psychViewModel.errorLD.observe(this){ e ->
            e.message?.let { showAlertDialog(it) }
        }

        binding.progressBarAdmPsych.visibility = View.VISIBLE
        psychViewModel.getPsychologistsPendingForApproval()
    }

    private fun updatePsychStatus(isAccepted: Boolean, psychId: String) {
        psychViewModel.updatePsychStatus(isAccepted,psychId)
        binding.progressBarAdmPsych.visibility = View.VISIBLE
        psychViewModel.getPsychologistsPendingForApproval()
    }

    private fun showAlertDialog(message:String){
        val builder = AlertDialog.Builder(this).setTitle(message).setNeutralButton("OK",null)
        builder.create().show()
    }

    fun promptConfirmation(isAccepted: Boolean, psychName: String, psychId: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("¿Está seguro que desea ${if (isAccepted) "aceptar" else "rechazar"} al psicólogo: $psychName?")
            .setPositiveButton("Sí") { _, _ ->
                updatePsychStatus(isAccepted,psychId)
            }
            .setNegativeButton("No", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}