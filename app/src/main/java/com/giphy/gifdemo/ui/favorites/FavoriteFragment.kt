package com.giphy.gifdemo.ui.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.giphy.gifdemo.adapter.FavoriteGifAdapter
import com.giphy.gifdemo.databinding.FragmentFavoriteBinding
import com.giphy.gifdemo.utils.invisible
import com.giphy.gifdemo.utils.visible
import com.giphy.gifdemo.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private  val viewModel: FavoriteViewModel by viewModels()

    private lateinit var binding:FragmentFavoriteBinding

    lateinit var favGifAdapter : FavoriteGifAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favGifAdapter = FavoriteGifAdapter { id, _ ->
            viewModel.removeFromFavorite(id)
        }
        binding.favoriteList.adapter = favGifAdapter


        viewModel.getAllFavGiphyGif().observe(viewLifecycleOwner){

            binding.emptyView.root.visibleIf(!it.isNotEmpty())

            favGifAdapter.setListData(it)
            favGifAdapter.notifyDataSetChanged()

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }


}