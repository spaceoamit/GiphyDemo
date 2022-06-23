package com.giphy.gifdemo.ui.trending

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.giphy.gifdemo.network.ApiService
import com.giphy.gifdemo.paging.GiphyDataSource
import com.giphy.gifdemo.repository.GifDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gifDataRepository: GifDataRepository
) : ViewModel() {

    private var pageConfig = PagingConfig(pageSize = 10, maxSize = 100, enablePlaceholders = false)

    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
        get() = _query


    fun getSearchingData(query: String?) {
        query?.let {
            Log.e("TAG", "query : $query")
            _query.postValue(query)
        }
    }

    var trendingGif = Pager(config = pageConfig,
        pagingSourceFactory = { GiphyDataSource(apiService) })
        .flow
        .asLiveData()
        .cachedIn(viewModelScope)

}