package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding

class EditPsyProfileViewHolder(root:View) : ViewHolder(root){
    private val binding = FragmentEditpsyprofileBinding.bind(root)
    val psyProfileImg = binding.psyProfileImg
    val psyImgUrl = binding.psyImgUrl
    val psyRole = binding.psyRole
    val selectPictureFromGalleryBtn = binding.selectPictureFromGalleryBtn
    val selectPictureFromCamBtn = binding.selectPictureFromCamBtn
    val psyEmail = binding.psyEmail
    val psyName = binding.psyName
    val psyGenre = binding.psyGenre
    val psyDescr = binding.psyDescr
    val editScheduleBtn = binding.editScheduleBtn
    val saveChangesBtn = binding.saveChangesBtn
}