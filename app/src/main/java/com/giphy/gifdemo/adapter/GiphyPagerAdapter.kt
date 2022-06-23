package com.giphy.gifdemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.giphy.gifdemo.ui.favorites.FavoriteFragment
import com.giphy.gifdemo.ui.trending.TrendingFragment

/**
 * @Author: Amit Patoliya
 * @Date: 21/06/22
 */
class GiphyPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        if(position== 1){
            return FavoriteFragment.newInstance()
        }
        return TrendingFragment.newInstance()
    }



}