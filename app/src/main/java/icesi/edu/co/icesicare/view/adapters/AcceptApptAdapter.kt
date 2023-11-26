package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import icesi.edu.co.icesicare.view.fragments.AcceptAppointmentFragment
import icesi.edu.co.icesicare.viewholder.AcceptApptViewHolder
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


class AcceptApptAdapter(fragment: AcceptAppointmentFragment) :
    RecyclerView.Adapter<AcceptApptViewHolder>() {

    private var parentFragment: AcceptAppointmentFragment = fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptApptViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accept_appt_card, parent, false)
        return AcceptApptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return AppointmentsRepository.apptsLiveData.value!!.size
    }

    override fun onBindViewHolder(holder: AcceptApptViewHolder, position: Int) {
        val appt = AppointmentsRepository.apptsLiveData.value?.get(position)//view model???

        appt?.let{apptIt ->

            holder.motiveTV.text = apptIt.motive

            val dateFormat = DateTimeFormatter.ofPattern("dd \'de\' MMMM yyyy HH:mm",
                Locale("es", "CO"))

            val date = apptIt.date
            val dateAsLocalDateTime: LocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

            holder.dateTV.text = dateAsLocalDateTime.format(dateFormat).toString()

            holder.rejectBtn.setOnClickListener{
                parentFragment.updateAppointment(apptIt.id,isAccepted = false,isCanceled = true)
            }

            holder.acceptBtn.setOnClickListener{
                parentFragment.updateAppointment(apptIt.id,isAccepted = true,isCanceled = false)
            }

            val student = AppointmentsRepository.studRelatedApptLiveData.value?.get(appt.studentId)

            student?.let {
                val fullName:String = it.name + " " + it.lastname
                holder.nameStudentTV.text = fullName

                if(student.profileImageURL != null && student.profileImageURL != ""){
                    Glide.with(parentFragment).load(it.profileImageURL).into(holder.imageStudentIV)
                }

            }
        }
    }
}