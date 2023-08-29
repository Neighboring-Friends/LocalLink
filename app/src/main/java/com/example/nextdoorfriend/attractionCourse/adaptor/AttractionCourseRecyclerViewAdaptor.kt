package com.example.nextdoorfriend.attractionCourse.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attractionCourse.AttractionCourse
import com.example.nextdoorfriend.databinding.ItemAttractionCourseBinding

class AttractionCourseRecyclerViewAdaptor(val itemList: MutableList<AttractionCourse>): RecyclerView.Adapter<AttractionCourseRecyclerViewAdaptor.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAttractionCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AttractionCourse) {
            binding.apply {
                attractionCourseNameTextView.text = data.courseNm
                attractionCourseDistanceTextView.text = data.distance
                attractionCourseTimeTextView.text = data.time
                itemItemAttractionCourseRecyclerView.adapter = AttractionCourseItemRecyclerViewAdapter(data.tourCourse)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}