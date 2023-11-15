package icesi.edu.co.icesicare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.databinding.ActivityMainBinding
import icesi.edu.co.icesicare.view.fragments.ChatsFragment
import icesi.edu.co.icesicare.view.fragments.HomeFragment
import icesi.edu.co.icesicare.view.fragments.StudentProfileFragment
import icesi.edu.co.icesicare.view.fragments.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val homeStud= HomeFragment.newInstance()
    private val schedule= ScheduleFragment.newInstance()
    private val chats= ChatsFragment.newInstance()
    private val profile= StudentProfileFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val user= Firebase.auth.currentUser
        if(user == null){
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        //if (user.uid)


        showFragment(homeStud)


        binding.navbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homemenu->{
                    showFragment(homeStud)
                }
                R.id.schedulemenu->{
                    showFragment(schedule)
                }
                R.id.chatsmenu->{
                    showFragment(chats)
                }
                R.id.profilemenu->{
                    showFragment(profile)
                }

            }
            true
        }


    }

    fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}