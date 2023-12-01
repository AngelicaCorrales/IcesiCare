package icesi.edu.co.icesicare.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesicare.databinding.CardAcceptApptBinding

class AcceptApptViewHolder(root: View):ViewHolder(root) {
    private val binding = CardAcceptApptBinding.bind(root)
    val imageStudentIV = binding.imageStudentIV
    val rejectBtn = binding.rejectBtn
    val acceptBtn = binding.acceptBtn
    val nameStudentTV = binding.nameStudentTV
    val dateTV = binding.dateTV
    val motiveTV = binding.motiveTV
}