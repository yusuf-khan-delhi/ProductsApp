package com.example.demo.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemResponse)

    @Query("Select * from favorites")
    fun getAllItems(): LiveData<List<ItemResponse>>

    @Query("SELECT * from favorites WHERE id= :id")
    fun getItemById(id: Int?):  LiveData<List<ItemResponse?>?>

}