package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.viewholder.PsychViewHolder

class PsychAdapter : RecyclerView.Adapter<PsychViewHolder> {

    private lateinit var parentActivity:MakeAppointmentActivity

    constructor(rvActivity:MakeAppointmentActivity):super(){
        parentActivity = rvActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsychViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.make_appointment_psych_general, parent, false)
        return PsychViewHolder(view)
    }

    override fun getItemCount(): Int {
        return PsychRepository.psychsLiveData.value!!.size
    }

    override fun onBindViewHolder(holder: PsychViewHolder, position: Int) {
        val psych = PsychRepository.psychsLiveData.value?.get(position)

        psych?.let{
            holder.namePsychTV.text = psych.name
            holder.descriptionPsychTV.text = psych.description
            //Set image for psychologists
            holder.makeAppointmentBtn.setOnClickListener{
                parentActivity.showFragmentPsychDetail(psych)
            }
        }
    }
}