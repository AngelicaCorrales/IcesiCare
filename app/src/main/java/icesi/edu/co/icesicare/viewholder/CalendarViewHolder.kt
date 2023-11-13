package icesi.edu.co.icesicare.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.view.adapters.CalendarAdapter
import java.time.LocalDate


class CalendarViewHolder(
    itemView: View,
    private val onItemListener: CalendarAdapter.OnItemListener,
    days: ArrayList<LocalDate?>
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val days: ArrayList<LocalDate?>
    val parentView: View
    val dayOfMonth: TextView

    init {
        parentView = itemView.findViewById<View>(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        itemView.setOnClickListener(this)
        this.days = days
    }

    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, days?.get(adapterPosition))
    }
}