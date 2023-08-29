package com.example.nextdoorfriend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.attraction.AttractionActivity
import com.example.nextdoorfriend.attractionCourse.AttractionCourseActivity
import com.example.nextdoorfriend.chatlist.ChatListFragment
import com.example.nextdoorfriend.databinding.ActivityMainBinding
import com.example.nextdoorfriend.mypage.MyPageFragment
import com.example.nextdoorfriend.userlist.UserFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userFragment = UserFragment()
    private val chatlistFragment = ChatListFragment()
    private val mypageFragment = MyPageFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.userList -> {
                    replaceFragment(userFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.chatroomList -> {
                    replaceFragment(chatlistFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.myPage -> {
                    replaceFragment(mypageFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }

        binding.testBtn.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.frameLayout, fragment)
                commit()
            }
    }
}