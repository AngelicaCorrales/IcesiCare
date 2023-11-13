package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.FragmentPsyProfileBinding

class PsyProfileViewHolder(root:View) : ViewHolder(root){
    private val binding = FragmentPsyProfileBinding.bind(root)
    val psyProfileImg = binding.psyProfileImg
    val psyEmail = binding.psyEmail
    val psyName = binding.psyName
    val psyGenre = binding.psyGenre
    val psyDescr = binding.psyDescr
    val saveChangesBtn = binding.editPsyBtn
}