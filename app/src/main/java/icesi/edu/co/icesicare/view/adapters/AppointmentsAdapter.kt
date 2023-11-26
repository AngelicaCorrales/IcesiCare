package icesi.edu.co.icesicare.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.entity.Appointments
import icesi.edu.co.icesicare.viewmodel.ElementAppointmentView
import java.text.SimpleDateFormat
import java.util.Date

class AppointmentsAdapter : RecyclerView.Adapter<ElementAppointmentView>(){
    val appointments = ArrayList<Appointment>()


    //    Construye los esqueletos de los items de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementAppointmentView {
        val inflater = LayoutInflater.from(parent.context)
        //ahi va el nombre del elemento de layout que se utiliza
        Log.e(">>>", " ass adapter  ")
        val view = inflater.inflate(R.layout.elementappointment, parent, false)
        Log.e(">>>", " ass adapter 2  ")
        val itemView = ElementAppointmentView(view)
        Log.e(">>>", " ass adapter 3  ")
        return itemView
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    //    Carga los datos en los esqueletos
    override fun onBindViewHolder(holder: ElementAppointmentView, position: Int) {
        val data = appointments[position]
        holder.namePsychologist.text = data.psychologistId
        holder.hourText.text = "12"
            //formatDay(data.date)

    }

    fun addItem(appointment: Appointment){
        appointments.add(appointment)
        notifyDataSetChanged()
    }

    fun addAppoinmentsList(appointmentList : ArrayList<Appointment>) {
        Log.e(">>>", "adapter add ")
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