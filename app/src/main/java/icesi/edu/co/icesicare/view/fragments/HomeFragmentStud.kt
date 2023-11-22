package icesi.edu.co.icesicare.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.databinding.FragmentHomePsychBinding
import icesi.edu.co.icesicare.databinding.FragmentHomeStudBinding
import icesi.edu.co.icesicare.databinding.FragmentSignUpBinding

class HomeFragmentStud : Fragment() {

    private lateinit var binding: FragmentHomeStudBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeStudBinding.inflate(inflater,container,false)

        binding.makeAppmBtn.setOnClickListener {
            val intent= Intent(activity, MakeAppointmentActivity::class.java)
            startActivity(intent) //if handling result needed, change to launch
        }

        binding.eventsBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Título del Diálogo") // Reemplaza con el título deseado
                .setMessage("Contenido del Diálogo") // Reemplaza con el contenido deseado
                .setPositiveButton("Aceptar") { _, _ ->
                    // Acciones cuando se hace clic en Aceptar
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    // Acciones cuando se hace clic en Cancelar
                }
                .create()

            dialog.show()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragmentStud()
    }
}