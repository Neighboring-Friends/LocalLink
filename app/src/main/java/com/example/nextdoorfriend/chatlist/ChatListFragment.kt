package com.example.nextdoorfriend.chatlist

import Key.Companion.DB_CHAT_ROOMS
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.chatdetail.ChatActivity
import com.example.nextdoorfriend.databinding.FragmentChatlistBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(R.layout.fragment_chatlist) {

    private lateinit var binding: FragmentChatlistBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatlistBinding.bind(view)

        val chatListAdapter = ChatListAdapter { chatRoomItem ->
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.EXTRA_OTHER_USER_ID, chatRoomItem.otherUserId)
            intent.putExtra(ChatActivity.EXTRA_CHAT_ROOM_ID, chatRoomItem.chatRoomId)

            startActivity(intent)
        }
            binding.chatListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatListAdapter
        }

        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        val chatRoomDB = Firebase.database.reference.child(DB_CHAT_ROOMS).child(currentUserId)

        chatRoomDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatroomList = snapshot.children.map{
                    it.getValue(ChatRoomItem::class.java)
                }
                chatListAdapter.submitList(chatroomList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}