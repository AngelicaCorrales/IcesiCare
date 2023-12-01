package icesi.edu.co.icesicare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.model.repository.PsychRepository
import icesi.edu.co.icesicare.viewholder.PsychViewHolder

class PsychAdapter(rvActivity: MakeAppointmentActivity) : RecyclerView.Adapter<PsychViewHolder>() {

    private var parentActivity:MakeAppointmentActivity = rvActivity

    fun filterPsychsByName(name:String){
        PsychRepository.filterPsychsByName(name)
    }

    fun clearFilter(){
        PsychRepository.clearFilter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsychViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_make_appointment_psych_general, parent, false)
        return PsychViewHolder(view)
    }

    override fun getItemCount(): Int {
        return PsychRepository.psychsLiveData.value!!.size
    }

    override fun onBindViewHolder(holder: PsychViewHolder, position: Int) {
        val psych = PsychRepository.psychsLiveData.value?.get(position)//view model???

        psych?.let{
            holder.namePsychTV.text = psych.name
            holder.descriptionPsychTV.text = psych.description

            if(it.profileImageURL != null){
                Glide.with(parentActivity).load(it.profileImageURL).into(holder.psychProfileImgView)
            }

            holder.makeAppointmentBtn.setOnClickListener{
                parentActivity.showFragmentPsychDetail(psych)
            }
        }
    }
}