package icesi.edu.co.icesicare.view.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.util.CalendarUtils
import icesi.edu.co.icesicare.viewholder.CalendarViewHolder
import java.time.LocalDate


class CalendarAdapter(
    private val days: ArrayList<LocalDate?>,
    private val onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = 70

        return CalendarViewHolder(view, onItemListener, days)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days?.get(position)
        if (date == null) holder.dayOfMonth.text = "" else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.selectedDate) holder.parentView.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getItemCount(): Int {
        return days!!.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }
}


