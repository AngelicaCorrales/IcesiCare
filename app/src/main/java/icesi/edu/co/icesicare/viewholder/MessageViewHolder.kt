package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.MessageBinding

class MessageViewHolder(root : View) : ViewHolder(root){

    private val binding = MessageBinding.bind(root)

    val contactName = binding.contactName
    val contactHour = binding.messageHour
    val contactMess = binding.message
    val meName = binding.meName
    val meHour = binding.meHour
    val meMess = binding.messageMe
    val contactLayout : ConstraintLayout = binding.contactMess
    val meLayout : ConstraintLayout = binding.meMess
}