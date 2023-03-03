package com.example.demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemRepository: ItemsRepository) : ViewModel(){

    val itemsLiveData get() = itemRepository.itemsLiveData

    fun getItems() {
        viewModelScope.launch {
          itemRepository.getItems()
        }
    }

}
