package com.example.nextdoorfriend.attraction

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attraction.Func.getAny
import com.example.nextdoorfriend.databinding.ActivityAttractionDetailBinding
import com.google.android.material.tabs.TabLayout

class AttractionDetailActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityAttractionDetailBinding.inflate(layoutInflater)
    }
    private lateinit var attraction: Attraction

    private val detailFragment by lazy {
        AttractionDetailFragment(attraction)
    }
    private val recruitFragment = RecruitFragment()

    private val tab1Text by lazy {
        getString(R.string.attraction_detail_tab_1)
    }
    private val tab2Text by lazy {
        getString(R.string.attraction_detail_tab_2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        attraction = intent.getAny("attraction", Attraction::class.java)

        binding.apply {
            nameTextView.text = attraction.attractName
            subTextView.text = attraction.address
        }

        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragment_layout, detailFragment)
                commit()
            }

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .apply {
                        replace(R.id.fragment_layout, if (tab?.text == tab1Text) detailFragment else recruitFragment)
                        commit()
                    }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}