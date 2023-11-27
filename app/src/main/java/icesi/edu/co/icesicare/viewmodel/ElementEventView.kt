package icesi.edu.co.icesicare.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.databinding.ElementvieweventsBinding


class ElementEventView(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = ElementvieweventsBinding.bind(root)
    val nameEventText = binding.eventNameText
    val hourText = binding.eventDateText
    val typeText = binding.eventTypeText
    val img = binding.eventeImage

}