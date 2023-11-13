package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.activities.MainActivity
import icesi.edu.co.icesicare.activities.MakeAppointmentActivity
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentPsyProfileBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PsyProfileFragment  : Fragment() {

    private lateinit var fragmentActivity: MainActivity
    private lateinit var binding: FragmentPsyProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentActivity = activity as MainActivity
        binding = FragmentPsyProfileBinding.inflate(inflater,container,false)

        showLoading(true)

        val psychologistId = "tdapPPL5ut9eyOzOkIso"

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(psychologistId)
            withContext(Dispatchers.Main) {
                showLoading(false)
                psy?.let {
                    binding.psyEmail.text = psy.email
                    binding.psyName.text = psy.name
                    binding.psyGenre.text = psy.genre
                    binding.psyDescr.text = psy.description

                    psy.profileImageURL?.let { imageUrl ->
                        if (imageUrl.isNotEmpty()) {
                            Glide.with(this@PsyProfileFragment).load(imageUrl).into(binding.psyProfileImg)
                        }
                    }
                }
            }
        }

        binding.editPsyBtn.setOnClickListener {
            val intent= Intent(activity, PsyProfileActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
        val negationVisibility = if (isLoading) View.INVISIBLE else View.VISIBLE

        binding.contentLayout.visibility = negationVisibility
        for (i in 0 until binding.contentLayout.childCount) {
            val child = binding.contentLayout.getChildAt(i)
            child.visibility = negationVisibility
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(): PsyProfileFragment{
            return PsyProfileFragment()
        }
    }
}