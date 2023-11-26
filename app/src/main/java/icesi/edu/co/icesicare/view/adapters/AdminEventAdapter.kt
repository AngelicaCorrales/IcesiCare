package icesi.edu.co.icesicare.view.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
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
        holder.eventHoursTV.text = eventDateTime.hour.toString().plus(":").plus(eventDateTime.minute.toString())
        holder.eventCatLocTV.text = event.category.plus("-").plus(event.space)

        if(event.imageURL != ""){
            Glide.with(parentActivity)
                .load(event.imageURL)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        holder.backgroundCL.background = resource
                    }
                })
        }
    }
}