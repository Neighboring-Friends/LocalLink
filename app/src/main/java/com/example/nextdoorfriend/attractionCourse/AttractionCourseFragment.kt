package com.example.nextdoorfriend.attractionCourse

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attraction.Attraction
import com.example.nextdoorfriend.attraction.AttractionLoader
import com.example.nextdoorfriend.databinding.FragmentAttractionCourseBinding

class AttractionCourseFragment : Fragment(R.layout.fragment_attraction_course) {

    private lateinit var activity: AttractionCourseActivity

    private lateinit var binding: FragmentAttractionCourseBinding
    private val attractionLoader by lazy {
        AttractionLoader(activity)
    }

    private val majorAttractionList = mutableListOf<Attraction>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionCourseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAttractionCourseBinding.bind(view)

    }
}