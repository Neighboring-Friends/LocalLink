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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.binding.itemItemAttractionCourseRecyclerView.adapter =
            AttractionCourseItemRecyclerViewAdapter(itemList[position].tourCourse,
                when(position) {
                    0 -> listOf(R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14)
                    1 -> listOf(R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25)
                    else -> listOf(R.drawable.c31, R.drawable.c32, R.drawable.c33, R.drawable.c34, R.drawable.c35, R.drawable.c36, R.drawable.c37)
                })
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}
