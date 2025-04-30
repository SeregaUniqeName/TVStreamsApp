package com.example.tvstreamsapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tvstreamsapp.data.local.model.TVChannelDb

@Dao
interface ChannelsDao {

    @Query("SELECT * FROM tvchanneldb")
    fun getChannels(): List<TVChannelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addChannels(channels: List<TVChannelDb>)
}