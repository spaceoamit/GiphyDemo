package com.giphy.gifdemo.ui.trending

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.giphy.gifdemo.R
import com.giphy.gifdemo.adapter.BaseLoadStateAdapter
import com.giphy.gifdemo.adapter.TrendingGifAdapter
import com.giphy.gifdemo.databinding.FragmentTrendingBinding
import com.giphy.gifdemo.paging.GiphyDataSource
import com.giphy.gifdemo.utils.invisible
import com.giphy.gifdemo.utils.isConnected
import com.giphy.gifdemo.utils.visible
import com.giphy.gifdemo.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class TrendingFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingFragment()
    }

    private lateinit var binding: FragmentTrendingBinding

    private val viewModel: TrendingViewModel by activityViewModels()

    @Inject
    lateinit var gifAdapter: TrendingGifAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        setObservers()

    }

    private fun setObservers() {



        lifecycleScope.launchWhenCreated {
            gifAdapter.loadStateFlow.collectLatest { loadstate ->
                binding.swipeToRefresh.isRefreshing = false

                when (loadstate.refresh) {
                    is LoadState.Loading -> {
                        Log.e("TAG","Loading")
                        binding.progressLoading.visible()

                    }
                    is LoadState.NotLoading -> {
                        Log.e("TAG","Not Loading")
                        binding.progressLoading.invisible()
                        (binding.emptyView.root.findViewById<TextView>(R.id.textViewMessage)).text =
                            context?.getString(R.string.no_data_search)
                        binding.emptyView.root.visibleIf(
                            loadstate.append.endOfPaginationReached
                                    &&
                                    gifAdapter.itemCount < 1
                        )

                    }
                    is LoadState.Error -> {

                        Log.e("TAG","Error message")
                        binding.swipeToRefresh.isRefreshing = false
                        binding.emptyView.root.visible()
                        binding.progressLoading.invisible()

                    }

                }
            }
        }

        viewModel.trendingGif.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenStarted {
                gifAdapter.submitData(it)
            }
        }


        viewModel.query.observe(viewLifecycleOwner) {
            GiphyDataSource.searchQuery = it
            gifAdapter.refresh()
            Log.e("TAG", "Is it making issue for orientation survive")
        }



    }




    private fun setListeners() {

        binding.trendingList.adapter = gifAdapter
        val footerAdapter = BaseLoadStateAdapter(gifAdapter)
        binding.trendingList.adapter = gifAdapter.withLoadStateFooter(footerAdapter)

        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
            if(requireActivity().isConnected) gifAdapter.refresh()
        }

    }
}