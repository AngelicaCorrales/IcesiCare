package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.dto.out.MessageOutDTO
import icesi.edu.co.icesicare.viewholder.MessageViewHolder

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>(){

    private var messages : ArrayList<MessageOutDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = layoutInflater.inflate(R.layout.message, parent, false)

        val holder = MessageViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        if (messages[position].authorId == Firebase.auth.currentUser!!.uid){
            holder.meLayout.visibility = View.VISIBLE
            holder.contactLayout.visibility = View.GONE
            holder.meHour.text = messages[position].hour
            holder.meMess.text = messages[position].message
            holder.meName.text = messages[position].authorName

        }else{
            holder.contactLayout.visibility = View.VISIBLE
            holder.meLayout.visibility = View.GONE
            holder.contactHour.text = messages[position].hour
            holder.contactMess.text = messages[position].message
            holder.contactName.text = messages[position].authorName
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(messagesToAdd : ArrayList<MessageOutDTO>){
        messages = messagesToAdd
        notifyItemInserted(messages.lastIndex)
    }
}