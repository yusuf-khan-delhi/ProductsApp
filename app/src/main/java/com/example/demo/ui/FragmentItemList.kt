package com.example.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.utils.Constants.ITEM_DATA
import com.example.demo.ui.adapter.ItemsAdapter
import com.example.demo.ui.viewmodel.ItemsViewModel
import com.example.demo.network.NetworkResult
import com.example.demo.databinding.FragmentItemListBinding
import com.example.demo.db.ItemResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentItemList : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private val itemsViewModel by viewModels<ItemsViewModel>()
    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        if(itemsViewModel.itemsLiveData.value == null) itemsViewModel.getItems()
    }

    private fun bindObservers() {
        itemsViewModel.itemsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = GONE
                    adapter = ItemsAdapter(context, it.data as ArrayList<ItemResponse>, ::onItemClicked)
                    binding.recyclerview.adapter = adapter
                    binding.recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = GONE
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = VISIBLE

                }
            }
        }
    }

    private fun onItemClicked(itemsResponse: ItemResponse){
        val bundle = Bundle()
        bundle.putString(ITEM_DATA, Gson().toJson(itemsResponse))
        FragmentItemDetailDialog.newInstance(bundle).show(parentFragmentManager, FragmentItemDetailDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}