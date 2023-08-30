package com.example.nextdoorfriend.attractionCourse

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentAttractionCourseBinding
import com.example.nextdoorfriend.attractionCourse.adaptor.AttractionCourseRecyclerViewAdaptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AttractionCourseFragment : Fragment(R.layout.fragment_attraction_course) {

    private lateinit var activity: AttractionCourseActivity

    private val attractionCourseLoader by lazy {
        AttractionCourseLoader()
    }

    private val attractionCourseList = mutableListOf<AttractionCourse>()

    private val targetNameList =
        listOf(
            "데이트코스",
            "시장여행길",
            "북구역사속으로")

    private lateinit var getAllJob: Job

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionCourseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAttractionCourseBinding.bind(view)

        binding.attractionCourseRecyclerView.apply{
            adapter = AttractionCourseRecyclerViewAdaptor(attractionCourseList)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            attractionCourseLoader.getSome(5) {
//                attractionCourseList += it
//                CoroutineScope(Dispatchers.Main).launch {
//                    binding.attractionCourseRecyclerView.adapter?.notifyItemInserted(
//                        attractionCourseList.size-1
//                    )
//                }
//            }.await()
//        }

        attractionCourseList.clear()

        getAllJob = CoroutineScope(Dispatchers.IO).launch {
            attractionCourseLoader.getAllWithFilter(
                {
                    (it.courseNm in targetNameList)
                },
                {
                    attractionCourseList += it
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.attractionCourseRecyclerView.adapter?.notifyItemInserted(
                            attractionCourseList.size-1
                        )
                    }
                }
            ).await()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllJob.cancel()
    }
}