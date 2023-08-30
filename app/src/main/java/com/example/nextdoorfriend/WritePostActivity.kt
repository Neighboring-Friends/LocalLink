package com.example.nextdoorfriend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nextdoorfriend.databinding.ActivityWritePostBinding

class WritePostActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityWritePostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            backImageView.setOnClickListener {
                finish()
            }
            submitButton.setOnClickListener {
                Toast.makeText(this@WritePostActivity, "post~", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}