package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.icesicare.databinding.ChatsFragmentBinding

class ChatsFragment: Fragment() {

    private lateinit var binding: ChatsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatsFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance():ChatsFragment{
            return ChatsFragment()
        }
    }

}