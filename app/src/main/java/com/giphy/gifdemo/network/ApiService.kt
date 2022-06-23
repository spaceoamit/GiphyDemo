package com.giphy.gifdemo.network

import com.giphy.gifdemo.BuildConfig
import com.giphy.gifdemo.models.GifrootModel
import com.giphy.gifdemo.utils.CommonData.LIMIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Amit Patoliya
 * @Date: 21/06/22
 */

interface ApiService {

    @GET("v1/gifs/trending")
    suspend fun getTrendingResponse(
        @Query("api_key") key:String = BuildConfig.GIPHY_API_KEY,
        @Query("limit") limit:Int = LIMIT,
        @Query("offset")offset:Int

    ): Response<GifrootModel>

    @GET("v1/gifs/search")
    suspend fun getSearchResponse(
        @Query("api_key") key:String = BuildConfig.GIPHY_API_KEY,
        @Query("limit") limit:Int= LIMIT,
        @Query("offset")offset:Int,
        @Query("q")searchText:String
    ):Response<GifrootModel>
}