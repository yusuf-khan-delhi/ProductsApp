package com.example.demo.network

import com.example.demo.db.ItemResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ItemsApi {

    @GET("products")
    suspend fun getItems(@Query("limit") limit: Int): Response<List<ItemResponse>>
}
