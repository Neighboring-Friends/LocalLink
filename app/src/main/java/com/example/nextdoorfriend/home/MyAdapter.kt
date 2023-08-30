package com.example.nextdoorfriend.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.ActivityHomeviewpagerBinding
import com.example.nextdoorfriend.databinding.HomeItemRecyclerviewBinding


class MyViewHolder(val binding: HomeItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas.size
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
//            = MyViewHolder( HomeItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = MyViewHolder( HomeItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.binding.root.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(getColor(context, R.color.transparent)))
            dialog.setContentView(R.layout.dialog_send)

            val yesBtn = dialog.findViewById<Button>(R.id.yes_btn)

            yesBtn.setOnClickListener {
                Toast.makeText(context, context.getString(R.string.dialog_submit_message), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.show()
        }
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas[position]
    }
}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        if (index % 3 == 0) //left, top, right, bottom
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 0)

        ViewCompat.setElevation(view, 20.0f)

    }
}