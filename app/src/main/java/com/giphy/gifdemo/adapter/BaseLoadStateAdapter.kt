package com.giphy.gifdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import com.giphy.gifdemo.databinding.LayoutNetworkStateItemBinding

/**
 * Created by Amit Patoliya
 */
class BaseLoadStateAdapter(
    private val adapter: PagingDataAdapter<*, *>
) : LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder =
        NetworkStateItemViewHolder(
            LayoutNetworkStateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            adapter.retry()
        }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}