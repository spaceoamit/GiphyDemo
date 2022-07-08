package com.giphy.gifdemo.di

import com.giphy.gifdemo.adapter.TrendingGifAdapter
import com.giphy.gifdemo.database.AppDatabase
import com.giphy.gifdemo.repository.GifDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * @Author: Amit Patoliya
 * @Date: 22/06/22
 */
@Module
@InstallIn(FragmentComponent::class)
object ActivityModule {
    @Provides
    fun provideAdapter(mGifRepository:GifDataRepository): TrendingGifAdapter = TrendingGifAdapter(mGifRepository)

}