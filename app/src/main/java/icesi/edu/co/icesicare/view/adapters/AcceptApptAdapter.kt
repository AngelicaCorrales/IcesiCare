package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_accept_appt, parent, false)
        return AcceptApptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return AppointmentsRepository.apptsLiveData.value!!.size
    }

    override fun onBindViewHolder(holder: AcceptApptViewHolder, position: Int) {
        val appt = AppointmentsRepository.apptsLiveData.value?.get(position)//view model???

        appt?.let{apptIt ->

            holder.motiveTV.text = apptIt.motive

            val date = apptIt.date

            val formatterDate = DateTimeFormatter.ofPattern("dd 'de' MMMM yyyy HH:mm", Locale("es", "CO"))
            val formattedDate = date.format(formatterDate)
            holder.dateTV.text = formattedDate



            val student = AppointmentsRepository.studRelatedApptLiveData.value?.get(appt.studentId)

            student?.let {
                val fullName:String = it.name + " " + it.lastname
                holder.nameStudentTV.text = fullName

                if(student.profileImageURL != null && student.profileImageURL != ""){
                    Glide.with(parentFragment).load(it.profileImageURL).into(holder.imageStudentIV)
                }

                holder.rejectBtn.setOnClickListener{
                    parentFragment.updateAppointment(apptIt.id,isAccepted = false,isCanceled = true)
                }

                holder.acceptBtn.setOnClickListener{
                    parentFragment.updateAppointment(apptIt.id,isAccepted = true,isCanceled = false)
                    //Logged user here is psychologist
                    parentFragment.initializeChat(student.id, Firebase.auth.currentUser?.uid.toString())
                }

            }
        }
    }
}