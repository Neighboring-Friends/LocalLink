package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentAttractionDetailBinding

class AttractionDetailFragment(val data: Attraction) : Fragment(R.layout.fragment_attraction_detail) {

    private lateinit var activity: AttractionDetailActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionDetailActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAttractionDetailBinding.bind(view)

        binding.apply {
            attractNameTv.text = data.attractName
            addressTv.text = data.address
            attractContentsTv.text = data.attractContents
            telTv.text = data.tel
            emailTv.text = data.email
            homepageTv.text = data.homepage
            attr01Tv.text = data.attr01
            attr02Tv.text = data.attr02
            attr03Tv.text = data.attr03
            attr04Tv.text = data.attr04
            attr05Tv.text = data.attr05
        }
    }
}
