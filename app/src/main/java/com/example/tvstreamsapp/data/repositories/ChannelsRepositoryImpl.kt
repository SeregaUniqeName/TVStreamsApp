package com.example.tvstreamsapp.data.repositories

import android.content.Context
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.local.model.TVChannelDb
import com.example.tvstreamsapp.data.utils.mapToDomain
import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val channelsDao: ChannelsDao,
) : ChannelsRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var id = 0

    private val dataFlow: Flow<List<TVChannel>> = flow {
        delay(3000)
        val channels = channelsDao.getChannels()
        if (channels.isEmpty()) {
            fillDatabase()
        }
        emit(channelsDao.getChannels().map { it.mapToDomain() }.toList())
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList<TVChannel>().toList()
    )


    private fun fillDatabase() {
        val channels = readAssetFile(context)
        channelsDao.addChannels(channels)
    }

    private fun readAssetFile(context: Context): List<TVChannelDb> {
        val channelList = mutableListOf<TVChannelDb>()
        val reader = context.assets.open(CHANNELS_FILE).bufferedReader()
        var line: String?
        var currentName = ""
        var currentStreamUrl = ""
        var currentTvChannel: TVChannelDb
        while (reader.readLine().also { line = it } != null) {
            when {
                line!!.startsWith(EXTINF) -> {
                    currentName = line!!.substringAfter(",")
                }

                line!!.startsWith(HTTP) -> {
                    currentStreamUrl = line!!
                    currentTvChannel = TVChannelDb(
                        channelName = currentName,
                        streamUri = currentStreamUrl,
                        iconUrl = "https://picsum.photos/id/$id/150"
                    )
                    channelList.add(currentTvChannel)
                    id++
                }
            }
        }
        return channelList
    }

    companion object {
        private const val CHANNELS_FILE = "Channels.txt"
        private const val EXTINF = "#EXTINF"
        private const val HTTP = "https"
    }

    override fun getChannels(): Flow<List<TVChannel>> {
        return dataFlow
    }

}