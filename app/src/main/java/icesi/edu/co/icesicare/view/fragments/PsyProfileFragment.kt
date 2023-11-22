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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.activities.PsychologistMainActivity
import icesi.edu.co.icesicare.databinding.FragmentPsyProfileBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PsyProfileFragment  : Fragment() {

    private lateinit var fragmentActivity: PsychologistMainActivity
    private lateinit var binding: FragmentPsyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentActivity = activity as PsychologistMainActivity
        binding = FragmentPsyProfileBinding.inflate(inflater,container,false)

        showLoading(true)

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(Firebase.auth.currentUser?.uid.toString())
            withContext(Dispatchers.Main) {
                showLoading(false)
                psy?.let {
                    binding.psyEmail.text = psy.email
                    binding.psyName.text = psy.name
                    binding.psyGenre.text = psy.genre
                    binding.psyDescr.text = psy.description

                    psy.profileImageURL?.let { imageUrl ->
                        if (imageUrl.isNotEmpty()) {
                            Glide.with(this@PsyProfileFragment).load(imageUrl).into(binding.profileImage)
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

    override fun onResume() {
        super.onResume()
        refreshPsychologistData()
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

    private fun refreshPsychologistData() {
        showLoading(true)

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(Firebase.auth.currentUser?.uid.toString())
            withContext(Dispatchers.Main) {
                showLoading(false)
                updateViews(psy)
            }
        }
    }

    private fun updateViews(psy: Psychologist?) {
        psy?.let {
            binding.psyEmail.text = it.email
            binding.psyName.text = it.name
            binding.psyGenre.text = it.genre
            binding.psyDescr.text = it.description

            it.profileImageURL?.let { imageUrl ->
                if (imageUrl != "") {
                    Glide.with(this@PsyProfileFragment).load(imageUrl).into(binding.profileImage)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): PsyProfileFragment{
            return PsyProfileFragment()
        }
    }
}