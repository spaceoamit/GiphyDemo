package com.giphy.gifdemo.repository

import com.giphy.gifdemo.database.AppDatabase
import com.giphy.gifdemo.database.models.FavoritesGifBean
import com.giphy.gifdemo.network.ApiService
import javax.inject.Inject

/**
 * @Author: Amit Patoliya
 * @Date: 21/06/22
 */
class GifDataRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase

) {
    fun removeFavorite(id: String) {
        appDatabase.getFavoriteData().delete(id)
    }

    fun setFavorite(favoriteBean: FavoritesGifBean) {
        appDatabase.getFavoriteData().addToFavorite(favoriteBean)
    }

    fun isFavorite(id: String):Boolean {
        return appDatabase.getFavoriteData().checkIsFavorite(id)

    }


}