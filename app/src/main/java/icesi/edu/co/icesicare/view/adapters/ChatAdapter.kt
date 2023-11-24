package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.dto.ChatDTO
import icesi.edu.co.icesicare.viewholder.ChatViewHolder

class ChatAdapter : RecyclerView.Adapter<ChatViewHolder>() {

    private var chats : ArrayList<ChatDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = layoutInflater.inflate(R.layout.chat, parent, false)

        val holder = ChatViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.usrName.text = chats[position].usrName
        holder.lastMessage.text = chats[position].lastMessage
        holder.lastMessHour.text = chats[position].lastMessHour

        if(chats[position].usrImage != ""){
            Glide.with(holder.usrImage.context).load(chats[position].usrImage).into(holder.usrImage)
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun addChat(chatsToAdd : ArrayList<ChatDTO>){
        chats = chatsToAdd
        notifyItemInserted(chats.lastIndex)
    }

}