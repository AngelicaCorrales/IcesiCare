package icesi.edu.co.icesicare.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.entity.Appointments
import icesi.edu.co.icesicare.viewmodel.ElementAppointmentView
import java.text.SimpleDateFormat
import java.util.Date

class AppointmentsAdapter : RecyclerView.Adapter<ElementAppointmentView>(){
    val appointments = ArrayList<Appointments>()


    //    Construye los esqueletos de los items de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementAppointmentView {
        val inflater = LayoutInflater.from(parent.context)
        //ahi va el nombre del elemento de layout que se utiliza
        val view = inflater.inflate(R.layout.elementappointment, parent, false)
        val itemView = ElementAppointmentView(view)
        return itemView
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    //    Carga los datos en los esqueletos
    override fun onBindViewHolder(holder: ElementAppointmentView, position: Int) {
        val data = appointments[position]
        holder.namePsychologist.text = data.psychologistId
        holder.hourText.text = formatDay(data.date)

    }

    fun addItem(appointment: Appointments){
        appointments.add(appointment)
        notifyDataSetChanged()
    }

    fun addAppoinmentsList(appointmentList : ArrayList<Appointments>) {
        appointments.clear()
        appointments.addAll(appointmentList)
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDay(date : Date) : String{
        date.hours = date.hours - 5
        val formatDay = SimpleDateFormat("dd/MM/yyyy")

        return formatDay.format(date)
    }


}