package icesi.edu.co.icesicare.view.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.FragmentWeeklyCalendarBinding
import icesi.edu.co.icesicare.util.CalendarUtils
import icesi.edu.co.icesicare.util.CalendarUtils.daysInWeekArray
import icesi.edu.co.icesicare.util.CalendarUtils.monthYearFromDate
import icesi.edu.co.icesicare.view.adapters.CalendarAdapter
import java.time.LocalDate


class WeeklyCalendarFragment : Fragment() , CalendarAdapter.OnItemListener {

    lateinit var binding:FragmentWeeklyCalendarBinding
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeeklyCalendarBinding.inflate(inflater, container, false)
        initWidgets()
        CalendarUtils.selectedDate.value = LocalDate.now()
        setWeekView()

        previousWeekAction()
        nextWeekAction()

        return binding.root
    }


    private fun initWidgets() {
        calendarRecyclerView =  binding.calendarRecyclerView
        monthYearText = binding.monthYearTV
    }

    private fun setWeekView() {
        CalendarUtils.selectedDate.value?.let {
            monthYearText?.setText( monthYearFromDate(it))
            val days: ArrayList<LocalDate?> = daysInWeekArray(it)
            val calendarAdapter = CalendarAdapter(days, this)
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)
            calendarRecyclerView!!.layoutManager = layoutManager
            calendarRecyclerView!!.adapter = calendarAdapter

        }

    }

    private fun previousWeekAction() {
        binding.previousWeekBtn.setOnClickListener {
            CalendarUtils.selectedDate.value = CalendarUtils.selectedDate.value!!.minusWeeks(1)
            setWeekView()
        }

    }

    private fun nextWeekAction() {
        binding.nextWeekBtn.setOnClickListener {
            CalendarUtils.selectedDate.value = CalendarUtils.selectedDate.value!!.plusWeeks(1)
            setWeekView()
        }

    }

    override fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate.value = date
        setWeekView()
    }

    companion object{
        fun newInstance(): WeeklyCalendarFragment{
            return WeeklyCalendarFragment()
        }
    }

}