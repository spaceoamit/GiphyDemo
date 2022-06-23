package com.giphy.gifdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giphy.gifdemo.database.models.FavoritesGifBean

/**
 * @Author: Amit Patoliya
 * @Date: 22/06/22
 */

@Database(entities = [FavoritesGifBean::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteData(): RoomDao
}