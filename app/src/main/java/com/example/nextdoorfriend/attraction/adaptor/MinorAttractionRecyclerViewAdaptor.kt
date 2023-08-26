package com.example.quoridor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attraction.Attraction
import com.example.nextdoorfriend.databinding.ItemMajorAttractionBinding
import com.example.nextdoorfriend.databinding.ItemMinorAttractionBinding

class MinorAttractionRecyclerViewAdaptor(val itemList: ArrayList<Attraction>): RecyclerView.Adapter<MinorAttractionRecyclerViewAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding by lazy {
            ItemMinorAttractionBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_minor_attraction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]

        val name = data.attractName
        val address = data.address

        holder.binding.attractNameTextView.text = name
        holder.binding.attractSubTextView.text = address
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
}