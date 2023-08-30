package com.example.nextdoorfriend.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.attractionCourse.AttractionCourseActivity
import com.example.nextdoorfriend.banner.FoodPeopleActivity
import com.example.nextdoorfriend.banner.RecomendTourActivity

import com.example.nextdoorfriend.databinding.ItemMainBannerBinding

class HomeAdapter(private val context: Context, val bannerList: ArrayList<HomeItem>) : RecyclerView.Adapter<HomeAdapter.PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_banner, parent, false))
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val currentItem = bannerList[position]
        holder.bind(currentItem)

        // 이미지 클릭 리스너 설정
        holder.bannerImageView.setOnClickListener {
            // 클릭한 이미지에 대한 처리
            when(position){
                0 -> {
                    val intent = Intent(context, FoodPeopleActivity::class.java)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, FoodPeopleActivity::class.java)
                    context.startActivity(intent)
                }
                2-> {
                    val intent = Intent(context, AttractionCourseActivity::class.java)
                    context.startActivity(intent)
                }
                else ->{

                }
            }
        }
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerImageView: ImageView = itemView.findViewById(R.id.banner_image)
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.text_title2)

        fun bind(item: HomeItem) {
            bannerImageView.setImageResource(item.imageResId)
            titleTextView.text = item.maintext
            subtitleTextView.text = item.plustext
        }
    }
}