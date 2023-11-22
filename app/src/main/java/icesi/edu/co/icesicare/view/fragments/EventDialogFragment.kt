package icesi.edu.co.icesicare.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.FragmentEventDialogBinding
import icesi.edu.co.icesicare.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Date

class EventDialogFragment : DialogFragment() {

    private val viewModel : EventViewModel by viewModels()
    private lateinit var binding: FragmentEventDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentEventDialogBinding.inflate(inflater,container,false)

        viewModel.getEvent("0trz7Ttzhd6g0SGg4vY0") //este id no debería ser así
        viewModel.eventLV.observe(viewLifecycleOwner){
            binding.eventTitle.text = it.name
            binding.eventType.text = it.category
            binding.eventDate.text = formatDay(it.date)
            binding.eventHour.text = formatHour(it.date)
            binding.eventLocation.text = it.space

            if(it.imageURL != ""){
                Glide.with(this).load(it.imageURL).into(binding.eventImage)
            }
        }

        binding.backDialogBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setStyle(STYLE_NORMAL, R.style.Theme_IcesiCare)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatHour(date : Date) : String{
        val formatHour = SimpleDateFormat("HH:mm:ss")

        return formatHour.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDay(date : Date) : String{
        date.hours = date.hours - 5
        val formatDay = SimpleDateFormat("dd/MM/yyyy")

        return formatDay.format(date)
    }

    companion object {
        @JvmStatic
        fun newInstance():EventDialogFragment{
            return EventDialogFragment()
        }
    }
}