package com.giphy.gifdemo.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giphy.gifdemo.database.AppDatabase
import com.giphy.gifdemo.database.models.FavoritesGifBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val appDatabase: AppDatabase
) : ViewModel() {


    fun getAllFavGiphyGif(): LiveData<List<FavoritesGifBean>> {
        return appDatabase.getFavoriteData().getAllFavGiphy()
    }

    fun removeFromFavorite(id:String) {
        appDatabase.getFavoriteData().delete(id)
    }

}