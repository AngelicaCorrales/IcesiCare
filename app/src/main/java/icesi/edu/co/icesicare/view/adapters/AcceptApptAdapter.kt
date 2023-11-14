package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AcceptAppointmentActivity
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.model.entity.Appointment
import icesi.edu.co.icesicare.model.repository.AppointmentsRepository
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.view.fragments.AcceptAppointmentFragment
import icesi.edu.co.icesicare.viewholder.AcceptApptViewHolder
import icesi.edu.co.icesicare.viewholder.PsychViewHolder

class AcceptApptAdapter : RecyclerView.Adapter<AcceptApptViewHolder> {

    private lateinit var parentFragment: AcceptAppointmentFragment

    constructor(fragment:AcceptAppointmentFragment):super(){
        parentFragment = fragment
    }

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

            holder.dateTV.text = apptIt.date.toString()

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

                if(student.profileImageURL != null){
                    Glide.with(parentFragment).load(it.profileImageURL).into(holder.imageStudentIV)
                }

            }
        }
    }
}