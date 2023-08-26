package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentAttractionBinding
import com.example.quoridor.adapter.MajorAttractionRecyclerViewAdaptor
import com.example.quoridor.adapter.MinorAttractionRecyclerViewAdaptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AttractionFragment : Fragment(R.layout.fragment_attraction) {

    private lateinit var activity: AttractionActivity

    private lateinit var binding: FragmentAttractionBinding
    private val attractionLoader by lazy {
        AttractionLoader(activity)
    }

    private val majorAttractionList = arrayListOf<Attraction>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAttractionBinding.bind(view)

        binding.majorAttractionRecyclerView.apply{
            adapter = MajorAttractionRecyclerViewAdaptor(majorAttractionList)
        }
        binding.minorAttractionRecyclerView.apply {
            adapter = MinorAttractionRecyclerViewAdaptor(majorAttractionList)
        }

        CoroutineScope(Dispatchers.IO)
            .launch {
                for (i in 1..5) {
                    majorAttractionList += attractionLoader.load(i).await()
                    withContext(Dispatchers.Main) {
                        binding.majorAttractionRecyclerView.adapter?.notifyItemInserted(
                            i - 1
                        )
                        binding.minorAttractionRecyclerView.adapter?.notifyItemInserted(
                            i - 1
                        )
                    }
                }
            }
    }
}