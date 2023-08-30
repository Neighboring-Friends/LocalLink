package com.example.nextdoorfriend.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ActivityHomeviewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.activity_homeviewpager) {

    private lateinit var binding: ActivityHomeviewpagerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityHomeviewpagerBinding.bind(view)

        val imageItems = arrayListOf(HomeItem(R.drawable.image1,getString(R.string.banner_1_main),getString(R.string.banner_1_sub))
            ,HomeItem(R.drawable.image2,getString(R.string.banner_2_main),getString(R.string.banner_2_sub)),
            HomeItem(R.drawable.image3,getString(R.string.banner_3_main),getString(R.string.banner_3_sub)))

        binding.viewPager.adapter = HomeAdapter(this.requireContext(),imageItems) // 어댑터 생성
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
        ) {
                tab, position ->
            binding.viewPager.currentItem = tab.position
        }.attach()

        //프레그 먼트 만들기
        val datas = mutableListOf<String>()
        for (i in 1..4) {
            datas.add("I find someone to eat together $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerview.layoutManager = layoutManager
        val adapter = MyAdapter(this.requireContext(), datas)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(MyDecoration(activity as Context))

    }



}
