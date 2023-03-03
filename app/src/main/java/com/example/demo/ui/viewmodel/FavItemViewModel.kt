package com.example.demo.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.repository.FavItemRepository
import com.example.demo.db.ItemResponse
import kotlinx.coroutines.launch

class FavItemViewModel : ViewModel() {

    fun insert(context: Context, favItems: ItemResponse)
    {
        viewModelScope.launch{
            FavItemRepository.insert(context, favItems)
        }
    }

    fun getAllFavItemList(context: Context): LiveData<List<ItemResponse>>
    {
        return FavItemRepository.getAllItemData(context)
    }

    fun getItemById(context: Context, id: Int?): LiveData<List<ItemResponse?>?>
    {
        return FavItemRepository.getItemById(context, id)
    }

    fun destroyDbInstance(){
        FavItemRepository.destroyDbInstance()
    }

}