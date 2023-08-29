package com.example.nextdoorfriend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nextdoorfriend.attraction.AttractionActivity
import com.example.nextdoorfriend.attractionCourse.AttractionCourseActivity
import com.example.nextdoorfriend.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            attractionBtn.setOnClickListener {
                startActivity(Intent(this@TestActivity, AttractionActivity::class.java))
            }
            attractionCourseBtn.setOnClickListener {
                startActivity(Intent(this@TestActivity, AttractionCourseActivity::class.java))
            }
            writeFab.setOnClickListener {
                startActivity(Intent(this@TestActivity, WritePostActivity::class.java))
            }
        }

    }
}