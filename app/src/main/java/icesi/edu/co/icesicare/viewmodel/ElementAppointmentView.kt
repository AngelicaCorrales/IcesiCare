package icesi.edu.co.icesicare.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.databinding.ElementappointmentBinding

class ElementAppointmentView(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = ElementappointmentBinding.bind(root)
    val namePsychologist = binding.psychologistNameText
    val hourText = binding.hourText

}