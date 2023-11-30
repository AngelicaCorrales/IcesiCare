package icesi.edu.co.icesicare.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.viewholder.ElementAppointmentHolder
import icesi.edu.co.icesicare.viewmodel.AppointmentData
import java.text.SimpleDateFormat
import java.util.Date

class AppointmentsAdapter : RecyclerView.Adapter<ElementAppointmentHolder>(){
    val appointments = ArrayList<AppointmentData>()
    var psychologistId : String = ""

    //    Construye los esqueletos de los items de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementAppointmentHolder {
        val inflater = LayoutInflater.from(parent.context)
        //ahi va el nombre del elemento de layout que se utiliza
        val view = inflater.inflate(R.layout.elementappointment, parent, false)
        val itemView = ElementAppointmentHolder(view)
        return itemView
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    //    Carga los datos en los esqueletos
    override fun onBindViewHolder(holder: ElementAppointmentHolder, position: Int) {
        val data = appointments[position]
        holder.namePsychologist.text = data.PsychologistName
        holder.hourText.text = formatDay(data.date)
        holder.viewDetailsBtn.setOnClickListener {
            psychologistId = data.psychologistId
        }
    }

    fun addItem(appointment: AppointmentData){
        appointments.add(appointment)
        notifyDataSetChanged()
    }

    fun addAppoinmentsList(appointmentList : ArrayList<AppointmentData>) {
        appointments.clear()
        appointments.addAll(appointmentList)
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDay(date : Date) : String{
        val formatHour = SimpleDateFormat("HH:mm")
        return formatHour.format(date)
    }


}