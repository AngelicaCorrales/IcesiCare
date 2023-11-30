package icesi.edu.co.icesicare.viewholder

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ElementappointmentBinding

class ElementAppointmentHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = ElementappointmentBinding.bind(root)
    val namePsychologist = binding.psychologistNameText
    val hourText = binding.hourText
    val viewDetailsBtn: Button = itemView.findViewById(R.id.viewDetailsBtn)
}