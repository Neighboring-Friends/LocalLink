package com.example.quoridor.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attraction.Attraction
import com.example.nextdoorfriend.attraction.AttractionDetailActivity
import com.example.nextdoorfriend.attraction.Func.putAny
import com.example.nextdoorfriend.databinding.ItemMajorAttractionBinding
import com.example.nextdoorfriend.databinding.ItemMinorAttractionBinding

class MinorAttractionRecyclerViewAdaptor(val context: Context, val itemList: MutableList<Attraction>): RecyclerView.Adapter<MinorAttractionRecyclerViewAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding by lazy {
            ItemMinorAttractionBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_minor_attraction, parent, false)

        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, AttractionDetailActivity::class.java)
            intent.putAny("attraction", itemList[viewHolder.adapterPosition])
            context.startActivity(intent)
        }

        return viewHolder
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