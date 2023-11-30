package icesi.edu.co.icesicare.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityAdminEventsBinding
import icesi.edu.co.icesicare.model.entity.Event
import icesi.edu.co.icesicare.view.fragments.AdminEventsAddUpdateFragment
import icesi.edu.co.icesicare.view.fragments.AdminEventsListFragment

class AdminEventsActivity : AppCompatActivity() {


    private val eventsListFragment = AdminEventsListFragment.newInstance()
    private val addEventFragment = AdminEventsAddUpdateFragment.newInstance(false,null)

    private val binding by lazy{
        ActivityAdminEventsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.admEvntSearchBtn.setOnClickListener{
            binding.admEvntBackBtn.visibility = View.GONE
            binding.admEvntSearchBtn.visibility = View.GONE
            binding.admEvntCancelSearchBtn.visibility = View.VISIBLE
            binding.admEvntSearchView.visibility = View.VISIBLE
            binding.admEvntSearchView.requestFocus()

            imm.showSoftInput(binding.admEvntSearchView, 0)
        }

        binding.admEvntCancelSearchBtn.setOnClickListener{
            binding.admEvntBackBtn.visibility = View.VISIBLE
            binding.admEvntSearchBtn.visibility = View.VISIBLE
            binding.admEvntCancelSearchBtn.visibility = View.GONE
            binding.admEvntSearchView.visibility = View.GONE
            binding.admEvntSearchView.setQuery("",false)
            eventsListFragment.clearFilter()
        }

        binding.admEvntSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    eventsListFragment.filterEventsByName(newText)
                    return true
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                imm.hideSoftInputFromWindow(binding.admEvntSearchView.windowToken,0)
                binding.admEvntSearchView.clearFocus()
                if (query != null) {
                    eventsListFragment.filterEventsByName(query)
                    return true
                }
                return false
            }
        })


        setContentView(binding.root)
        showEventsListFragment()
    }

    private fun showFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.adminEventsFragmentContainer ,fragment).commit()
    }

    fun showEventsListFragment() {

        showFragment(eventsListFragment)
        binding.admEvntBackBtn.setOnClickListener {
            val intent= Intent(this,AdminMainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    fun showAddUpdateEventFragment(isUpdating:Boolean,event: Event?) {
        addEventFragment.isUpdating = isUpdating
        addEventFragment.event = event
        showFragment(addEventFragment)
        binding.admEvntBackBtn.setOnClickListener{
            showEventsListFragment()
        }
    }
}