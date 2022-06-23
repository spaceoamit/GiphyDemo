package com.giphy.gifdemo.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Amit Patoliya
 * @Date: 22/06/22
 */
@Entity
data class FavoritesGifBean(
    @PrimaryKey(autoGenerate = true)
    var uid :Int = 0,
    val id: String,
    val imageUrl:String
    )
