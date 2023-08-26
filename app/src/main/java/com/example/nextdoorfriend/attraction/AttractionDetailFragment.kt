package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentAttractionDetailBinding

class AttractionDetailFragment(val data: Attraction) : Fragment(R.layout.fragment_attraction_detail) {

    private lateinit var activity: AttractionDetailActivity

    private lateinit var binding: FragmentAttractionDetailBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionDetailActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attraction_detail, container, false)
        binding.item = data
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}