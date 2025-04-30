package com.example.tvstreamsapp.data.repositories

import android.content.Context
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.local.model.TVChannelDb
import com.example.tvstreamsapp.data.remote.ImageApiService
import com.example.tvstreamsapp.data.utils.mapToDomain
import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val channelsDao: ChannelsDao,
    private val apiService: ImageApiService,
) : ChannelsRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val channelsFlow: StateFlow<List<TVChannel>>
        get() = flow {
            val channels = channelsDao.getChannels()
            if (channels.isEmpty()) {
                fillDatabase()
            }
            emit(channelsDao.getChannels().map { it.mapToDomain() })
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private suspend fun fillDatabase() {
        val channels = readAssetFile(context)
        channelsDao.addChannels(channels)
    }

    private suspend fun readAssetFile(context: Context): List<TVChannelDb> {
        val channelList = mutableListOf<TVChannelDb>()
        val reader = context.assets.open(CHANNELS_FILE).bufferedReader()
        var line: String?
        var currentName = ""
        var currentStreamUrl = ""
        var currentTvChannel: TVChannelDb
        while (withContext(Dispatchers.IO) {
                reader.readLine()
            }.also { line = it } != null) {
            when {
                line!!.startsWith(EXTINF) -> {
                    currentName = line!!.substringAfter(",")
                }

                line!!.startsWith(HTTP) -> {
                    currentStreamUrl = line!!
                }
            }
            currentTvChannel = TVChannelDb(
                channelName = currentName,
                streamUri = currentStreamUrl,
                iconUrl = loadImageUrl(currentName)
            )
            channelList.add(currentTvChannel)

        }
        return channelList
    }

    private suspend fun loadImageUrl(query: String): String {
        return apiService.getImageUrl(query).item.imageUrl
    }

    companion object {
        private const val CHANNELS_FILE = "Channels.txt"
        private const val EXTINF = "#EXTINF"
        private const val HTTP = "http"
    }
}