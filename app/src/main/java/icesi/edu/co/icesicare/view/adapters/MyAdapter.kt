package icesi.edu.co.icesicare.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import icesi.edu.co.icesicare.view.fragments.AppointmentspsychologistFragment

internal class MyAdapter(var context: Context,val fm: FragmentManager, val totalTabs: Int): FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                AppointmentspsychologistFragment()
            }
            1 -> {
                AppointmentspsychologistFragment()
            }
            2 -> {
                AppointmentspsychologistFragment()
            }
            3 -> {
                AppointmentspsychologistFragment()
            }
            4 -> {
                AppointmentspsychologistFragment()
            }
            5 -> {
                AppointmentspsychologistFragment()
            }
            6 -> {
                AppointmentspsychologistFragment()
            }
            7 -> {
                AppointmentspsychologistFragment()
            }
            8 -> {
                AppointmentspsychologistFragment()
            }
            9 -> {
                AppointmentspsychologistFragment()
            }
            10 -> {
                AppointmentspsychologistFragment()
            }
            11 -> {
                AppointmentspsychologistFragment()
            }

            else -> getItem(position)

        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}