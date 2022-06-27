package com.giphy.gifdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giphy.gifdemo.models.Data
import com.giphy.gifdemo.network.ApiService
import com.giphy.gifdemo.utils.CommonData.LIMIT
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * @Author: Amit Patoliya
 * @Date: 21/06/22
 */
class GiphyDataSource @Inject constructor(
    private val apiService: ApiService,
) : PagingSource<Int, Data>() {

    private var offset = 0

    companion object {
        var searchQuery: String = ""
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        offset = params.key ?: 0

        try {

            val response = if (searchQuery.isNotBlank()) {
                apiService.getSearchResponse(offset = offset, searchText = searchQuery)
            } else {
                apiService.getTrendingResponse(offset = offset)
            }
            return if (response.isSuccessful && response.body() != null) {

                val giphyListBean = response.body()?.data

                LoadResult.Page(
                    data = giphyListBean!!,
                    prevKey = if (offset > LIMIT) offset - LIMIT else null,
                    nextKey = if (giphyListBean.isNotEmpty()) offset + LIMIT else null
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }

        } catch (e: IOException) {
            if (e is UnknownHostException) {
                return LoadResult.Error(Exception("No internet connection."))
            }
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int = 0


}