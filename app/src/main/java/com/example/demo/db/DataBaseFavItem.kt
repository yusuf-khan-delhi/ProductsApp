package com.example.demo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.demo.utils.Constants.ITEM_DB
import java.util.concurrent.locks.Lock

@Database(entities = [ItemResponse::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataBaseFavItem : RoomDatabase() {
    abstract fun getItemDao() : FavItemDao

    companion object {
        @Volatile private var INSTANCE: DataBaseFavItem? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(Lock) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private  fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DataBaseFavItem::class.java,
            ITEM_DB
        ).build()

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
