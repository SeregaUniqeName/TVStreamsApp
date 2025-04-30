package com.example.tvstreamsapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvstreamsapp.data.local.model.TVChannelDb

@Database(entities = [TVChannelDb::class], version = 1, exportSchema = false)
abstract class ChannelsDatabase : RoomDatabase() {

    abstract fun channelsDao(): ChannelsDao

    companion object {

        private var INSTANCE: ChannelsDatabase? = null
        private val lock: Any = Any()

        fun getInstance(context: Context): ChannelsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(lock) {
                INSTANCE?.let {
                    return it
                }
                val database = Room.databaseBuilder(
                    context = context,
                    klass = ChannelsDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }

        private const val DB_NAME = "channelsDatabase"
    }
}