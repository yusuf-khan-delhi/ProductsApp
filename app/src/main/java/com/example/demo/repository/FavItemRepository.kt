package com.example.demo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.demo.db.DataBaseFavItem
import com.example.demo.db.ItemResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class FavItemRepository {

    companion object{
        var dataBaseFavItem: DataBaseFavItem?=null

        private fun intializeDB(context: Context): DataBaseFavItem
        {
            return DataBaseFavItem.invoke(context)
        }

        suspend fun insert(context: Context, favItem: ItemResponse)
        {
            dataBaseFavItem = intializeDB(context)
            withContext(IO) {
                dataBaseFavItem!!.getItemDao().insertItem(favItem)
            }
        }

        fun getAllItemData(context: Context): LiveData<List<ItemResponse>>
        {
            dataBaseFavItem = intializeDB(context)
            return dataBaseFavItem!!.getItemDao().getAllItems()
        }

        fun getItemById(context: Context, id: Int?): LiveData<List<ItemResponse?>?>
        {
            dataBaseFavItem = intializeDB(context)
            return dataBaseFavItem!!.getItemDao().getItemById(id)
        }

        fun destroyDbInstance() {
            DataBaseFavItem.destroyInstance()
        }
    }
}