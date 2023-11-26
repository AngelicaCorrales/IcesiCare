package icesi.edu.co.icesicare.view.adapters

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AdminEventsActivity
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.viewholder.AdminEventViewHolder
import java.time.ZoneId


class AdminEventAdapter(activity:AdminEventsActivity) : RecyclerView.Adapter<AdminEventViewHolder>() {

    private val parentActivity:AdminEventsActivity = activity

    var events : List<Event> = arrayListOf()
        set(value){
            field =value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_admin_event_list, parent, false)
        return AdminEventViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("DEV",events.size.toString())
        return events.size
    }

    override fun onBindViewHolder(holder: AdminEventViewHolder, position: Int) {
        val event = events[position]

        holder.eventNameTV.text = event.name

        val eventDateTime = event.date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();

        holder.eventDayTV.text = eventDateTime.dayOfMonth.toString()
        holder.eventMonthTV.text = eventDateTime.month.toString().substring(0,3)

        val hourNum = eventDateTime.hour
        val hour = if (hourNum < 10) "0".plus(hourNum.toString()) else hourNum.toString()

        val minutesNum = eventDateTime.minute
        val minutes = if (minutesNum < 10) "0".plus(minutesNum.toString()) else minutesNum.toString()

        holder.eventHoursTV.text = hour.plus(":").plus(minutes)

        holder.eventCatLocTV.text = event.category.plus(" - ").plus(event.space)

        if(event.imageURL != ""){
            Glide.with(parentActivity).load(event.imageURL).into(holder.eventImgView)
            holder.eventImgView.visibility = View.VISIBLE
            val layoutParams = holder.topRelLayout.layoutParams
            layoutParams.height = 363.toPx
            holder.topRelLayout.layoutParams = layoutParams
        }
        else{
            holder.eventImgView.setImageDrawable(null)
            holder.eventImgView.visibility = View.GONE
            val layoutParams = holder.topRelLayout.layoutParams
            layoutParams.height = 82.toPx
            holder.topRelLayout.layoutParams = layoutParams
        }
    }

    /**
     * Converts DP units to PX units
     */
    private val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}