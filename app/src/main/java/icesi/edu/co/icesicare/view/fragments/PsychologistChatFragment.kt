package icesi.edu.co.icesicare.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.R
import icesi.edu.co.icesicare.activities.PsychologistChatActivity
import icesi.edu.co.icesicare.activities.StudentChatActivity
import icesi.edu.co.icesicare.databinding.FragmentPsychologistChatBinding
import icesi.edu.co.icesicare.databinding.FragmentStudentChatBinding
import icesi.edu.co.icesicare.view.adapters.ChatAdapter
import icesi.edu.co.icesicare.viewmodel.PsychologistChatsViewModel
import icesi.edu.co.icesicare.viewmodel.StudentChatsViewModel

class PsychologistChatFragment : Fragment() {

    private val viewModel : PsychologistChatsViewModel by viewModels()
    private lateinit var binding: FragmentPsychologistChatBinding
    private lateinit var adapter : ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPsychologistChatBinding.inflate(inflater, container, false)

        adapter = ChatAdapter(viewModel)
        viewModel.getChats(Firebase.auth.currentUser!!.uid)

        viewModel.chatSLV.observe(viewLifecycleOwner){
            adapter.addChat(it)
            binding.chatList.adapter = adapter
            binding.chatList.layoutManager = LinearLayoutManager(requireContext())
            binding.chatList.setHasFixedSize(true)
        }

        viewModel.chatId.observe(viewLifecycleOwner){
            val intent= Intent(requireActivity(), PsychologistChatActivity::class.java)
            intent.putExtra("chatId", it)
            startActivity(intent)
        }

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance():PsychologistChatFragment{
            return PsychologistChatFragment()
        }
    }

}