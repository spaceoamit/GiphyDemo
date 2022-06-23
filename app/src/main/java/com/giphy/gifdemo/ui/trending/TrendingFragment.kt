package com.giphy.gifdemo.ui.trending

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.giphy.gifdemo.R
import com.giphy.gifdemo.adapter.TrendingGifAdapter
import com.giphy.gifdemo.databinding.FragmentTrendingBinding
import com.giphy.gifdemo.paging.GiphyDataSource
import com.giphy.gifdemo.utils.invisible
import com.giphy.gifdemo.utils.visible
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
            gifAdapter.loadStateFlow.collectLatest {
                binding.swipeToRefresh.isRefreshing = false
                when (it.refresh) {
                    is LoadState.Loading -> {
                        binding.progressLoading.visible()
                        binding.txtNoResultMessage.invisible()
                    }
                    is LoadState.NotLoading -> {
                        binding.progressLoading.invisible()
                        binding.txtNoResultMessage.invisible()
                        if (gifAdapter.itemCount < 1){
                            binding.txtNoResultMessage.visible()
                        }else
                            binding.txtNoResultMessage.invisible()
                    }
                    is LoadState.Error -> {

                        binding.trendingList.visibility = View.GONE
                        binding.txtNoResultMessage.visible()
                        binding.progressLoading.invisible()
                        binding.txtNoResultMessage.apply {
                            visible()
                            text = context.getString(R.string.error_message)
                        }
                    }

                }
            }
        }

        viewModel.trendingGif.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenStarted {
                binding.progressLoading.invisible()
                gifAdapter.submitData(it)
            }
        }


        viewModel.query.observe(viewLifecycleOwner) {
            GiphyDataSource.searchQuery = it
            gifAdapter.refresh()
        }



    }

    private fun setListeners() {

        binding.trendingList.adapter = gifAdapter

        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
            gifAdapter.refresh()
        }

    }
}