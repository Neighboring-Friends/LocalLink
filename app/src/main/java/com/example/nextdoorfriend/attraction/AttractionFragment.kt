package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentAttractionBinding
import com.example.nextdoorfriend.attraction.adaptor.MajorAttractionRecyclerViewAdaptor
import com.example.nextdoorfriend.attraction.adaptor.MinorAttractionRecyclerViewAdaptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AttractionFragment : Fragment(R.layout.fragment_attraction) {

    private lateinit var activity: AttractionActivity

    private val attractionLoader by lazy {
        AttractionLoader(activity)
    }

    private val majorAttractionList = mutableListOf<Attraction>()
    private val minorAttractionList = mutableListOf<Attraction>()

    private val majorNameList =
        listOf(
            "2.28기념중앙공원",
            "83타워(83 TOWER DAEGU)",
            "갓바위",
            "강정고령보 - 디아크(The ARC)",
            "경북대학교 가로수길(백양로)")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAttractionBinding.bind(view)

        binding.majorAttractionRecyclerView.apply{
            adapter = MajorAttractionRecyclerViewAdaptor(activity, majorAttractionList)
        }
        binding.minorAttractionRecyclerView.apply {
            adapter = MinorAttractionRecyclerViewAdaptor(activity, minorAttractionList)
        }

        CoroutineScope(Dispatchers.IO).launch {
            attractionLoader.getAllWithFilter(
                {
                    (it.attractName in majorNameList)
                },
                {
                    majorAttractionList += it
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.majorAttractionRecyclerView.adapter?.notifyItemInserted(
                            majorAttractionList.size - 1
                        )
                    }
                },
                {
                    CoroutineScope(Dispatchers.IO).launch {  }
                }
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            attractionLoader.getSomeWithFilter(
                15,
                {
                    it.attractName !in majorNameList
                },
                {
                    minorAttractionList += it
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.minorAttractionRecyclerView.adapter?.notifyItemInserted(
                            minorAttractionList.size - 1
                        )
                    }
                },
                {
                    CoroutineScope(Dispatchers.IO).launch {  }
                }
            )
        }
    }
}