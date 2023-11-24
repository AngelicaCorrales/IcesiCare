package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.icesicare.databinding.FragmentStudentChatBinding
import icesi.edu.co.icesicare.view.adapters.ChatAdapter
import icesi.edu.co.icesicare.viewmodel.StudentChatsViewModel

class StudentChatFragment : Fragment() {

    private val viewModel : StudentChatsViewModel by viewModels()
    private lateinit var binding: FragmentStudentChatBinding
    private lateinit var adapter : ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentChatBinding.inflate(inflater, container, false)

        adapter = ChatAdapter()
        viewModel.getChats()

        viewModel.chatSLV.observe(viewLifecycleOwner){
            adapter.addChat(it)
            binding.chatList.adapter = adapter
            binding.chatList.layoutManager = LinearLayoutManager(requireContext())
            binding.chatList.setHasFixedSize(true)
        }

        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance():StudentChatFragment{
            return StudentChatFragment()
        }
    }
}