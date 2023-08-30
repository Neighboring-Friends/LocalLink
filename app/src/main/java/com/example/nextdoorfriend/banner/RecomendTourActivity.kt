package com.example.nextdoorfriend.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nextdoorfriend.databinding.ActivityFoodtogetherBinding
import com.example.nextdoorfriend.databinding.ActivityTourRecomendBinding

class RecomendTourActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTourRecomendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTourRecomendBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}