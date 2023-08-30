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
import com.example.nextdoorfriend.databinding.ItemMinorAttractionBinding

class MinorAttractionRecyclerViewAdaptor(val context: Context, val itemList: MutableList<Attraction>): RecyclerView.Adapter<MinorAttractionRecyclerViewAdaptor.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMinorAttractionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Attraction) {
            binding.attractNameTextView.text = data.attractName
            binding.attractSubTextView.text = data.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMinorAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolder(binding)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, AttractionDetailActivity::class.java)
            intent.putAny("attraction", itemList[viewHolder.adapterPosition])
            intent.putExtra("position", viewHolder.adapterPosition)
            intent.putExtra("major", false)
            context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.binding.attractImageView.setImageResource(when(position) {
            0 -> R.drawable.n1
            1 -> R.drawable.n2
            2 -> R.drawable.n3
            else -> R.drawable.n4
        })
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}