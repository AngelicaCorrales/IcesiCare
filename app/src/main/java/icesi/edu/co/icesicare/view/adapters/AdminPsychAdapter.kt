package icesi.edu.co.icesicare.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AdminPsychologistsActivity
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.viewholder.AdminPsychViewHolder

class AdminPsychAdapter (private val parentActivity: AdminPsychologistsActivity) : RecyclerView.Adapter<AdminPsychViewHolder>() {

    var psychs:List<Psychologist> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminPsychViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_admin_psych,
            parent,
            false)
        return AdminPsychViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminPsychViewHolder, position: Int) {
        val psych = psychs[position]

        holder.admPsychNameTV.text = psych.name
        holder.admPsychEmailTV.text = psych.email

        if(psych.profileImageURL != "")
            Glide.with(parentActivity).load(psych.profileImageURL).into(holder.admPsychImgV)

        holder.admPsychRejectBtn.setOnClickListener{
            parentActivity.promptConfirmation(false,psych.name, psych.id)
        }

        holder.admPsychAcceptBtn.setOnClickListener{
            parentActivity.promptConfirmation(true,psych.name, psych.id)
        }

    }

    override fun getItemCount(): Int {
        return psychs.size
    }
}