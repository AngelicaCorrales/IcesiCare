package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.ChatBinding

class ChatViewHolder(root : View) : ViewHolder(root){

    private val binding = ChatBinding.bind(root)

    val usrName = binding.usrName

    val usrImage = binding.usrImage

    val lastMessage = binding.lastMessage

    val lastMessHour = binding.lastMessHour
}