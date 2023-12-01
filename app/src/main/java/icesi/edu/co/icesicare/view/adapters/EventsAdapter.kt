package icesi.edu.co.icesicare.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.util.DataDateUtils
import icesi.edu.co.icesicare.viewmodel.ElementEventView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

class EventsAdapter : RecyclerView.Adapter<ElementEventView>(){
    val events = ArrayList<Event>()


    //    Construye los esqueletos de los items de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementEventView {

        val inflater = LayoutInflater.from(parent.context)
        //ahi va el nombre del elemento de layout que se utiliza
        val view = inflater.inflate(R.layout.elementviewevents, parent, false)
        val itemView = ElementEventView(view)
        return itemView
    }

    override fun onBindViewHolder(holder: ElementEventView, position: Int) {
        val data = events[position]
        holder.nameEventText.text = data.name
        holder.typeText.text = data.category
        Glide.with(holder.img).load(events[position].imageId).into(holder.img)
        holder.hourText.text = DataDateUtils.formatHour(data.date)


    }
    fun addEventsList(appointmentList : ArrayList<Event>) {
        events.clear()
        events.addAll(appointmentList)
        Log.e(">>>", events.size.toString() + "  tamaño ")
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return  events.size
    }



}
