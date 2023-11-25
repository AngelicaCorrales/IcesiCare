package icesi.edu.co.icesicare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminEventsBinding
import icesi.edu.co.icesicare.databinding.FragmentAdminEventsListBinding
import icesi.edu.co.icesicare.view.fragments.AdminEventsAddUpdateFragment
import icesi.edu.co.icesicare.view.fragments.AdminEventsListFragment

class AdminEventsActivity : AppCompatActivity() {


    private val eventsListFragment = AdminEventsListFragment.newInstance()
    private val addEventFragment = AdminEventsAddUpdateFragment.newInstance(false,"")

    private val binding by lazy{
        ActivityAdminEventsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showEventsListFragment()
    }

    private fun showFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.adminEventsFragmentContainer ,fragment).commit()
    }

    fun showEventsListFragment(){ showFragment(eventsListFragment)}
    fun showAddEventFragment() {showFragment(addEventFragment)}


}