package com.giphy.gifdemo.ui.trending

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.giphy.gifdemo.network.ApiService
import com.giphy.gifdemo.paging.GiphyDataSource
import com.giphy.gifdemo.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private var pageConfig = PagingConfig(pageSize = 10, maxSize = 100, enablePlaceholders = false)

    val queryLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    fun getSearchingData(query: String?) {
        query?.let {
            Log.e("TAG", "query : $query")
            queryLiveData.postValue(query)
        }
    }

    var trendingGif = Pager(config = pageConfig,
        pagingSourceFactory = { GiphyDataSource(apiService) })
        .flow
        .asLiveData()
        .cachedIn(viewModelScope)

}