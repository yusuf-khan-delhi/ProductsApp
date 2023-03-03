package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.utils.Constants
import com.example.demo.ui.adapter.ItemsAdapter
import com.example.demo.databinding.FragmentFavoriteBinding
import com.example.demo.ui.viewmodel.FavItemViewModel
import com.example.demo.db.ItemResponse
import com.google.gson.Gson

class FragmentFavorite : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favItemViewModel: FavItemViewModel
    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favItemViewModel = ViewModelProvider(this)[FavItemViewModel::class.java]
        showFavItems()
    }

    private fun showFavItems() {
        context?.let {
            favItemViewModel.getAllFavItemList(it).observe(viewLifecycleOwner)
            { itemList ->
                if (itemList.isNotEmpty()) {
                    binding.tvNoItems.visibility = GONE
                    adapter = ItemsAdapter(it, itemList as ArrayList<ItemResponse>, ::onItemClicked)
                    binding.recyclerview.adapter = adapter
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }else{
                    binding.tvNoItems.visibility = VISIBLE
                }
            }
        }
    }

    private fun onItemClicked(itemsResponse: Any) {
        val bundle = Bundle()
        bundle.putString(Constants.ITEM_DATA, Gson().toJson(itemsResponse))
        bundle.putBoolean(Constants.IS_FROM_FAVORITE, true)
        FragmentItemDetailDialog.newInstance(bundle)
            .show(parentFragmentManager, FragmentItemDetailDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}