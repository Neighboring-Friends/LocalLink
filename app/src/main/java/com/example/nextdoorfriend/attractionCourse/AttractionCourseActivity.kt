package com.example.nextdoorfriend.attractionCourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ActivityAttractionCourseBinding

class AttractionCourseActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityAttractionCourseBinding.inflate(layoutInflater)
    }
    private val attractionFragment = AttractionCourseFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(attractionFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.fragment_layout, fragment)
                commit()
            }
    }
}