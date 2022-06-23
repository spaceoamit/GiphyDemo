package com.giphy.gifdemo.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.giphy.gifdemo.databinding.LayoutNetworkStateItemBinding
import com.giphy.gifdemo.utils.visibleIf

/**
 * Created by Amit Patoliya
 */
class NetworkStateItemViewHolder(
    binding: LayoutNetworkStateItemBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val progressBar = binding.progressView
    private val errorMessage = binding.textViewError
    private val retry = binding.buttonRetry.also {
        it.setOnClickListener {
            retryCallback.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.visibleIf(loadState is LoadState.Error)
        errorMessage.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMessage.text = (loadState as? LoadState.Error)?.error?.message
    }

}