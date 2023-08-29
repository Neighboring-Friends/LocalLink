package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentRecruitBinding
import com.example.nextdoorfriend.attraction.adaptor.MinorAttractionRecyclerViewAdaptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecruitFragment : Fragment(R.layout.fragment_recruit) {

    private lateinit var activity: AttractionDetailActivity

    private val attractionLoader by lazy {
        AttractionLoader(activity)
    }

    private val majorAttractionList = mutableListOf<Attraction>()

    private lateinit var getListJob: Job

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionDetailActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRecruitBinding.bind(view)

        binding.minorAttractionRecyclerView.apply {
            adapter = MinorAttractionRecyclerViewAdaptor(activity, majorAttractionList)
        }

        getListJob = CoroutineScope(Dispatchers.IO)
            .launch {
                if (majorAttractionList.size == 0) {
                    for (i in 1..5) {
                        majorAttractionList += attractionLoader.load(i).await()
                        withContext(Dispatchers.Main) {
                            binding.minorAttractionRecyclerView.adapter?.notifyItemInserted(
                                i - 1
                            )
                        }
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        getListJob.cancel()
    }
}