package com.example.tvstreamsapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TVChannelDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val channelName: String,
    @ColumnInfo val iconUrl: String,
    @ColumnInfo val streamUri: String,
    @ColumnInfo val isActive: Boolean = false
)