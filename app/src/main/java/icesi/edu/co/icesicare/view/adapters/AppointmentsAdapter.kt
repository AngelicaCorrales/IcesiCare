package icesi.edu.co.icesicare.view.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ElementappointmentBinding
import icesi.edu.co.icesicare.model.entity.Appointments

class AppointmentsAdapter: Adapter<AppointmentsVH>() {
    private val caughtAppointmen = ArrayList<Appointments>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elementappointment, parent, false)
        return AppointmentsVH(view)
    }

    override fun getItemCount(): Int = caughtAppointmen.size


    override fun onBindViewHolder(holder: AppointmentsVH, position: Int) {
        holder.psychologistName.text = caughtAppointmen[position].psychologistId
        holder.hour.text = caughtAppointmen[position].date.toString()
    }

    fun addAppointment(appointments:Appointments){
        caughtAppointmen.add(appointments)
        notifyDataSetChanged()
    }

    fun addAppoinmentsList(appointmentsList: ArrayList<Appointments>) {
        caughtAppointmen.clear()
        caughtAppointmen.addAll(appointmentsList)
        notifyDataSetChanged()
    }

}
class AppointmentsVH(item: View): RecyclerView.ViewHolder(item){
    private val binding = ElementappointmentBinding.bind(item)
    val psychologistName = binding.psychologistNameText
    val hour = binding.hourText
}