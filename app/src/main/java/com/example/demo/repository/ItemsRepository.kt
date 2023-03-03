package com.example.demo.repository

import androidx.lifecycle.MutableLiveData
import com.example.demo.db.ItemResponse
import com.example.demo.network.ItemsApi
import com.example.demo.network.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class ItemsRepository @Inject constructor(private val itemsApi: ItemsApi) {

    private val _itemsLiveData = MutableLiveData<NetworkResult<List<ItemResponse>>>()
    val itemsLiveData get() = _itemsLiveData

    suspend fun getItems() {
        _itemsLiveData.postValue(NetworkResult.Loading())
        val response = itemsApi.getItems(10)
        if (response.isSuccessful && response.body() != null) {
            _itemsLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _itemsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _itemsLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}
