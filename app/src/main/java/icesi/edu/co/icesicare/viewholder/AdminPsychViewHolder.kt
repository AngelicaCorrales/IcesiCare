package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.databinding.CardAdminPsychBinding

class AdminPsychViewHolder (root : View) : RecyclerView.ViewHolder(root){
    private val binding = CardAdminPsychBinding.bind(root)

    val admPsychNameTV = binding.admPsychNameTV
    val admPsychEmailTV = binding.admPsychEmailTV
    val admPsychImgV =  binding.admPsychImgV
    val admPsychAcceptBtn =  binding.admPsychAcceptBtn
    val admPsychRejectBtn =  binding.admPsychRejectBtn

}
