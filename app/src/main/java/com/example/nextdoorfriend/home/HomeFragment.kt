package com.example.nextdoorfriend.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.nextdoorfriend.MainActivity
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.WritePostActivity
import com.example.nextdoorfriend.attraction.adaptor.MajorAttractionRecyclerViewAdaptor
import com.example.nextdoorfriend.databinding.ActivityHomeviewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.activity_homeviewpager) {

    private lateinit var binding: ActivityHomeviewpagerBinding
    val imageItems = arrayListOf(HomeItem(R.drawable.image1,"Let's have lunch!","같이 밥 먹으며 친해져요")
        ,HomeItem(R.drawable.image2,"Find Helper","한국 생활 중 모르는 일들을 도와드립니다!"),
        HomeItem(R.drawable.image3,"tourist spot","같이 관광하며 친해져요"))

    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityHomeviewpagerBinding.bind(view)

        binding.viewPager.adapter = HomeAdapter(activity, imageItems) // 어댑터 생성
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
        ) {
                tab, position ->
            binding.viewPager.currentItem = tab.position
        }.attach()
    }

    // 뷰 페이저에 들어갈 아이템

}
