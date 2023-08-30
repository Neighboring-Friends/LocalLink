package com.example.nextdoorfriend.attraction.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attraction.Attraction
import com.example.nextdoorfriend.attraction.AttractionDetailActivity
import com.example.nextdoorfriend.attraction.Func.putAny
import com.example.nextdoorfriend.databinding.ItemMajorAttractionBinding

class MajorAttractionRecyclerViewAdaptor(val context: Context, val itemList: MutableList<Attraction>): RecyclerView.Adapter<MajorAttractionRecyclerViewAdaptor.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMajorAttractionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Attraction) {
            binding.attractNameTextView.text = data.attractName
            binding.attractSubTextView.text = data.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMajorAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolder(binding)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, AttractionDetailActivity::class.java)
            intent.putAny("attraction", itemList[viewHolder.adapterPosition])
            intent.putExtra("position", viewHolder.adapterPosition)
            intent.putExtra("major", true)
            context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.binding.attractImageView.setImageResource(when(position) {
            0 -> R.drawable.m1
            1 -> R.drawable.m2
            2 -> R.drawable.m3
            3 -> R.drawable.m4
            else -> R.drawable.m5
        })
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}