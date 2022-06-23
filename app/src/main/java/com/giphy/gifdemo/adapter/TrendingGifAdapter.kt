package com.giphy.gifdemo.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giphy.gifdemo.R
import com.giphy.gifdemo.database.AppDatabase
import com.giphy.gifdemo.database.models.FavoritesGifBean

import com.giphy.gifdemo.databinding.ItemGifBinding
import com.giphy.gifdemo.models.Data
import javax.inject.Inject


/**
 * @Author: Amit Patoliya
 * @Date: 21/06/22
 */

class TrendingGifAdapter @Inject constructor(
    private val appDatabase: AppDatabase
): PagingDataAdapter<Data, TrendingGifAdapter.ViewHolder>(DiffUtilCallBack()) {

    inner class ViewHolder(var binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            var url = getItem(position)?.images?.original?.url
            binding.apply {
                Glide.with(root)
                    .asGif()
                    .load(url)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(itemGifFile)
            }

            getItem(position)?.id?.let { giphyUrlId ->
                var isInFavorite = getItem(position)?.id?.let { appDatabase.getFavoriteData().checkIsFavorite(id = it) }
                binding.imgFavorite.apply {
                    if(isInFavorite == true) setImageResource(R.drawable.ic_favorite)
                    else setImageResource(R.drawable.ic_favorite_border)
                }
            }

            binding.imgFavorite.setOnClickListener {
                    getItem(position)?.id?.let{

                        var isInFavorite = getItem(position)?.id?.let { appDatabase.getFavoriteData().checkIsFavorite(id = it) }

                        if(isInFavorite == true){
                            appDatabase.getFavoriteData().delete(it)
                            binding.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
                        }else{

                            url?.let { url ->
                                var favoriteBean = FavoritesGifBean(id = it, imageUrl = url)
                                appDatabase.getFavoriteData().addToFavorite(favoriteBean)
                                binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                            }
                        }
                        //notifyItemChanged(position)

                    }
            }

        }

    }


    class DiffUtilCallBack :  DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }


}



private fun ImageView.setFavoriteImage(it: String, mydb: AppDatabase): Boolean {

        return mydb.getFavoriteData().checkIsFavorite(id = it)


}
