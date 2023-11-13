package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import icesi.edu.co.icesicare.activities.PsyProfileActivity
import icesi.edu.co.icesicare.databinding.FragmentEditpsyprofileBinding
import icesi.edu.co.icesicare.model.repository.PsychRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPsyProfileFragment : Fragment() {

    private lateinit var fragmentActivity: PsyProfileActivity
    private lateinit var binding: FragmentEditpsyprofileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentActivity = activity as PsyProfileActivity
        binding = FragmentEditpsyprofileBinding.inflate(inflater, container, false)

        val psychologistId = "tdapPPL5ut9eyOzOkIso"

        lifecycleScope.launch(Dispatchers.IO) {
            val psy = PsychRepository.fetchOnePsy(psychologistId)
            withContext(Dispatchers.Main) {
                psy?.let {
                    binding.psyEmail.text = psy.email.toEditable()
                    binding.psyName.text = psy.name.toEditable()
                    binding.psyGenre.text = psy.genre.toEditable()
                    binding.psyDescr.text = psy.description.toEditable()

                    psy.profileImageURL?.let { imageUrl ->
                        if (imageUrl.isNotEmpty()) {
                            Glide.with(this@EditPsyProfileFragment).load(imageUrl).into(binding.psyProfileImg)
                        }
                    }
                }
                fragmentActivity.showLoading(false)
            }
        }

        return binding.root
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    companion object {
        @JvmStatic
        fun newInstance(): EditPsyProfileFragment {
            return EditPsyProfileFragment()
        }
    }
}