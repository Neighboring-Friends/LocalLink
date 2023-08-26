package com.example.nextdoorfriend.attraction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nextdoorfriend.R
import com.example.nextdoorfriend.databinding.FragmentRecruitBinding
import com.example.quoridor.adapter.AttractionCourseRecyclerViewAdaptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecruitFragment : Fragment(R.layout.fragment_attraction_detail) {

    private lateinit var activity: AttractionDetailActivity

    private lateinit var binding: FragmentRecruitBinding
    private val attractionLoader by lazy {
        AttractionLoader(activity)
    }

    private val majorAttractionList = mutableListOf<Attraction>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as AttractionDetailActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recruit, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.minorAttractionRecyclerView.apply {
            adapter = AttractionCourseRecyclerViewAdaptor(activity, majorAttractionList)
        }

        CoroutineScope(Dispatchers.IO)
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
}