package com.example.nextdoorfriend.chatlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentChatlistBinding
import com.example.nextdoorfriend.databinding.FragmentUserlistBinding

class ChatListFragment : Fragment(R.layout.fragment_chatlist) {

    private lateinit var binding: FragmentChatlistBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatlistBinding.bind(view)

        val userListAdapter = ChatListAdapter()
        binding.chatListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
        userListAdapter.submitList(
            mutableListOf<ChatRoomItem?>().apply {
                add(ChatRoomItem("11", "22", "33"))
            }
        )
    }
}