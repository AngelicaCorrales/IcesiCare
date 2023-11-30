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
import icesi.edu.co.icesicare.util.CalendarUtils.toLocalDateTime
import icesi.edu.co.icesicare.view.fragments.AdminEventsListFragment
import icesi.edu.co.icesicare.viewholder.AdminEventViewHolder
import java.time.ZoneId


class AdminEventAdapter(activity:AdminEventsActivity, fragment: AdminEventsListFragment) : RecyclerView.Adapter<AdminEventViewHolder>() {

    private val parentActivity:AdminEventsActivity = activity
    private val parentFragment:AdminEventsListFragment = fragment

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

        val eventDateTime = event.date.toLocalDateTime

        holder.eventDayTV.text = eventDateTime.dayOfMonth.toString()
        holder.eventMonthTV.text = eventDateTime.month.toString().substring(0,3)

        val hourNum = eventDateTime.hour
        val hour = if (hourNum < 10) "0".plus(hourNum.toString()) else hourNum.toString()

        val minutesNum = eventDateTime.minute
        val minutes = if (minutesNum < 10) "0".plus(minutesNum.toString()) else minutesNum.toString()

        holder.eventHoursTV.text = hour.plus(":").plus(minutes)

        holder.eventCatLocTV.text = event.category.plus(" - ").plus(event.space)

        val layoutParams = holder.topRelLayout.layoutParams

        if(event.imageURL != ""){
            Glide.with(parentActivity).load(event.imageURL).into(holder.eventImgView)
            holder.eventImgView.visibility = View.VISIBLE
            layoutParams.height = 363.toPx
        }
        else{
            holder.eventImgView.setImageDrawable(null)
            holder.eventImgView.visibility = View.GONE
            layoutParams.height = 82.toPx
        }

        holder.topRelLayout.layoutParams = layoutParams

        // Buttons

        holder.deleteEventBtn.setOnClickListener{
            parentFragment.promptDeleteConfirmation(event)
        }

        holder.editEventBtn.setOnClickListener{
            parentFragment.showAddUpdateEventFragment(true,event)
        }
    }

    /**
     * Converts DP units to PX units
     */
    private val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}