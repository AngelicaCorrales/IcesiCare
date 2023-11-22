package icesi.edu.co.icesicare.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.FragmentEventDialogBinding
import icesi.edu.co.icesicare.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Date

class EventDialogFragment : DialogFragment() {

    private val viewModel : EventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val binding: FragmentEventDialogBinding = FragmentEventDialogBinding.inflate(inflater,container,false)

        viewModel.getEvent("0trz7Ttzhd6g0SGg4vY0")
        viewModel.eventLV.observe(viewLifecycleOwner){
            binding.eventTitle.text = it.name
            binding.eventType.text = it.category
            binding.eventDate.text = formatDay(it.date)
            binding.eventHour.text = formatHour(it.date)
            binding.eventLocation.text = it.space
        }

        return inflater.inflate(R.layout.fragment_event_dialog, container, false)
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
        fun newInstance():EventDialogFragment{
            return EventDialogFragment()
        }
    }
}