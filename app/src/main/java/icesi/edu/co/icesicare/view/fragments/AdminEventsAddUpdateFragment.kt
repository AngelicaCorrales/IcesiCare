package icesi.edu.co.icesicare.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.AdminEventsActivity
import icesi.edu.co.icesicare.databinding.FragmentAdminEventsAddUpdateBinding
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.util.CalendarUtils.toDate
import icesi.edu.co.icesicare.util.CalendarUtils.toLocalDateTime
import icesi.edu.co.icesicare.viewmodel.EventViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


private const val IS_UPDATING_KEY = "isUpdating"
private const val EVENT_ID_KEY = "event"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminEventsAddUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminEventsAddUpdateFragment (var isUpdating: Boolean, var event: Event?) : Fragment() {

    private lateinit var binding: FragmentAdminEventsAddUpdateBinding
    private lateinit var parentActivity: AdminEventsActivity
    private val eventsViewModel : EventViewModel by viewModels()
    private var colorNeutralWhite : Int = 0
    private var colorPurple : Int = 0

    private val launcher= registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ::onGalleryResult)

    private fun onGalleryResult(result:androidx.activity.result.ActivityResult) {
        val uri=result.data?.data
        Glide.with(this).load(uri).into(binding.eventImageIV)
        imageURL = uri.toString()
    }

    private var isImageChanged:Boolean = false
    private var imageURL :String = ""
    private var date: LocalDate? = null
    private var time: LocalTime? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentAdminEventsAddUpdateBinding.inflate(inflater, container, false)
        parentActivity = activity as AdminEventsActivity

        colorNeutralWhite = ContextCompat.getColor(this.requireContext(), R.color.neutral_white)
        colorPurple = ContextCompat.getColor(this.requireContext(), R.color.purple)

        binding.eventDateBtn.setOnClickListener{
            DatePickerFragment().show(childFragmentManager, "datePicker")
        }

        binding.eventTimeBtn.setOnClickListener{
            TimePickerFragment().show(childFragmentManager, "timePicker")
        }

        binding.eventImageBtn.setOnClickListener(){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            launcher.launch(intent)
        }

        if(isUpdating){
            if(event == null){
                throw Exception("Expected event != null but found null")
            }
            onCreateUpdateView()
        }
        else{
            onCreateAddView()
        }

        return binding.root
    }

    private fun onCreateUpdateView(){

        imageURL = event!!.imageURL

        val dateTime = event!!.date.toLocalDateTime

        date = dateTime.toLocalDate()
        time = dateTime.toLocalTime()

        binding.eventNameET.setText(event!!.name)
        binding.eventCategorySpin
        binding.eventCategorySpin.setSelection(
            findIndexOfSpinnerOption(binding.eventCategorySpin,event!!.category))
        binding.eventLocationET.setText(event!!.space)

        val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        binding.eventDateBtn.text = date!!.format(dateFormatter)

        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")
        binding.eventTimeBtn.text = time!!.format(timeFormatter)

        Glide.with(parentActivity).load(event!!.imageURL).into(binding.eventImageIV)

        binding.addUpdateEventBtn.text = "Guardar Cambios"
        binding.addUpdateEventBtn.setOnClickListener{

            var isOkToUpdate = true

            val name = binding.eventNameET.text.toString()
            if(name == "") {
                showAlertDialog("Es obligatorio especificar el nombre del evento.")
                isOkToUpdate = false
            }

            val space = binding.eventLocationET.text.toString()
            if(space == "" ){
                showAlertDialog("Es obligatorio especificar el espacio donde se llevará a cabo el evento.")
                isOkToUpdate = false
            }

            if(isOkToUpdate) {
                event!!.category = binding.eventCategorySpin.selectedItem.toString()
                event?.date  = LocalDateTime.of(date,time).toDate
                event!!.name = name
                event!!.space = space
                if(isImageChanged) event?.imageURL = imageURL

                eventsViewModel.eventLD.value = event

                eventsViewModel.eventLD.observe(viewLifecycleOwner){
                    if (it != null){
                        parentActivity.showEventsListFragment()
                        eventsViewModel.eventLD.removeObservers(viewLifecycleOwner)
                    }
                }

                event?.let { eventsViewModel.updateEvent(it, isImageChanged) }
            }
        }
    }

    private fun onCreateAddView(){
        binding.addUpdateEventBtn.text = "+ Añadir Evento"
        binding.addUpdateEventBtn.setOnClickListener{
            var isOkToCreate = true

            val category = binding.eventCategorySpin.selectedItem.toString()

            var finalDate:Date = Date()

            if(date == null || time == null){
                showAlertDialog("Es obligatorio especificar la fecha y hora del evento.")
                isOkToCreate = false
            }
            else{
                finalDate = LocalDateTime.of(date,time).toDate
            }

            val name = binding.eventNameET.text.toString()
            if(name == "") {
                showAlertDialog("Es obligatorio especificar el nombre del evento.")
                isOkToCreate = false
            }

            val space = binding.eventLocationET.text.toString()
            if(space == "" ){
                showAlertDialog("Es obligatorio especificar el espacio donde se llevará a cabo el evento.")
                isOkToCreate = false
            }

            if(isOkToCreate) {

                eventsViewModel.eventLD.value = event

                eventsViewModel.eventLD.observe(viewLifecycleOwner) {
                    if (it != null){
                        parentActivity.showEventsListFragment()
                        eventsViewModel.eventLD.removeObservers(viewLifecycleOwner)
                    }
                }

                eventsViewModel.createEvent(category, finalDate, name, imageURL, space)
            }
        }

    }

    private fun showAlertDialog(message:String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
        builder
            .setTitle(message)
            .setNeutralButton("OK") { dialog, which -> //Do nothing
            }

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            button.setTextColor(colorNeutralWhite)
            button.setBackgroundColor(colorPurple)
        }
        dialog.show()
    }

    /**
     * @return Int >= 0, the index of the specified option, -1 if the option was not found.
     */
    private fun findIndexOfSpinnerOption(spinner: Spinner,option:String):Int{

        val numOptions = spinner.count

        for(i in 0 until numOptions){
            val item:String = spinner.getItemAtPosition(i) as String

            if(item == option)
                return i
        }
        return -1
    }

    fun setTime(localTime: LocalTime?) {
        time = localTime
        binding.eventTimeBtn.text = time.toString()
    }

    fun setDate(localDate: LocalDate?) {
        date = localDate
        binding.eventDateBtn.text = date.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param isUpdating If an event is being updated
         * @param eventId id of the event being updated,should be null
         * if this fragment is used for creating an event.
         * @return A new instance of fragment AdminEventsAddUpdateFragment.
         */
        @JvmStatic fun newInstance(isUpdating: Boolean, event: Event?) =
                AdminEventsAddUpdateFragment(isUpdating, event)
    }
}


 class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        (parentFragment as AdminEventsAddUpdateFragment).setTime(LocalTime.of(hourOfDay,minute))
    }


}
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        (parentFragment as AdminEventsAddUpdateFragment).setDate(LocalDate.of(year,month,day))
    }
}