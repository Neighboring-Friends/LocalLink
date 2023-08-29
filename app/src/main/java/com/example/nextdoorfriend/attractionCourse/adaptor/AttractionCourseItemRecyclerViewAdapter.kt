package com.example.nextdoorfriend.attractionCourse.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ItemItemAttractionCourseBinding

class AttractionCourseItemRecyclerViewAdapter(val itemList: Array<String>): RecyclerView.Adapter<AttractionCourseItemRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemItemAttractionCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.itemItemAttractionNameTextView.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemItemAttractionCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}