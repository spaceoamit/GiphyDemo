package com.giphy.gifdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giphy.gifdemo.R
import com.giphy.gifdemo.database.models.FavoritesGifBean
import com.giphy.gifdemo.databinding.ItemGifBinding
import com.giphy.gifdemo.utils.alert
import com.giphy.gifdemo.utils.negativeButton
import com.giphy.gifdemo.utils.positiveButton

/**
 * @Author: Amit Patoliya
 * @Date: 22/06/22
 */
class FavoriteGifAdapter (
    private val onFavoriteClick : (String, Int) -> Unit
    ): RecyclerView.Adapter<FavoriteGifAdapter.ViewHolder>(){


    private var favGifList:List<FavoritesGifBean> = listOf()

    inner class ViewHolder(var binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            var url = favGifList[position].imageUrl
            binding.apply {
                Glide.with(root)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .into(itemGifFile)
            }


            binding.imgFavorite.setImageResource(R.drawable.ic_favorite)

            binding.imgFavorite.setOnClickListener {

                it.context.alert {
                    setTitle(context.getString(R.string.waring_messsage))
                    setMessage(context.getString(R.string.remove_message))
                    positiveButton {
                        onFavoriteClick.invoke(favGifList[position].id,position)
                    }
                    negativeButton { }
                }
            }

        }

    }

    override fun getItemCount(): Int = favGifList.size

    fun setListData(it: List<FavoritesGifBean>) {
        favGifList = it
    }
}