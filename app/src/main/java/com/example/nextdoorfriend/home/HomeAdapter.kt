package com.example.nextdoorfriend.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ItemMainBannerBinding

class HomeAdapter (val bannerList: ArrayList<HomeItem>) : RecyclerView.Adapter<HomeAdapter.PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_banner, parent, false))
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val currentItem = bannerList[position]
        holder.bind(currentItem)
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bannerImageView: ImageView = itemView.findViewById(R.id.banner_image)
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.text_title2)

        fun bind(item: HomeItem) {
            bannerImageView.setImageResource(item.imageResId)
            titleTextView.text = item.maintext
            subtitleTextView.text = item.plustext
        }
    }
}