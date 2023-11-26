package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.CardAdminEventListBinding

class AdminEventViewHolder(root: View) : ViewHolder(root){
    private val binding = CardAdminEventListBinding.bind(root)
    val eventDayTV = binding.eventDayTV
    val eventMonthTV = binding.eventMonthTV
    val eventNameTV = binding.eventNameTV
    val eventCatLocTV = binding.eventCatLocTV
    val eventHoursTV = binding.eventHoursTV
    val deleteEventBtn = binding.deleteEventBtn
    val editEventBtn = binding.editEventBtn
    val backgroundCL = binding.backgroundCL
}