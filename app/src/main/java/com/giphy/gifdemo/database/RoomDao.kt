package com.giphy.gifdemo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giphy.gifdemo.database.models.FavoritesGifBean

/**
 * @Author: Amit Patoliya
 * @Date: 22/06/22
 */
@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addToFavorite(favoriteBean: FavoritesGifBean)


    @Query("SELECT EXISTS (SELECT 1 from FavoritesGifBean where id =:id)")
     fun checkIsFavorite(id:String) : Boolean


    @Query("DELETE FROM FavoritesGifBean WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * FROM FavoritesGifBean")
    fun getAllFavGiphy():LiveData<List<FavoritesGifBean>>
}
