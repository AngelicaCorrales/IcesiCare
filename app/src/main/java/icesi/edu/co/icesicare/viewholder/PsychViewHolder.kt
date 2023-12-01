package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.CardMakeAppointmentPsychGeneralBinding
class PsychViewHolder(root:View) : ViewHolder(root){
    private val binding = CardMakeAppointmentPsychGeneralBinding.bind(root)
    val psychProfileImgView = binding.psychProfileImgView
    val namePsychTV = binding.namePsychTV
    val rolePsychTV = binding.rolePsychTV
    val descriptionPsychTV = binding.descriptionPsychTV
    val makeAppointmentBtn = binding.makeAppointmentBtn
}