package com.example.nextdoorfriend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.databinding.FragmentMypageBinding
import com.example.nextdoorfriend.databinding.FragmentTourBinding

class TourFragment :  Fragment(R.layout.fragment_tour) {

    private lateinit var binding: FragmentTourBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTourBinding.bind(view)
    }
}