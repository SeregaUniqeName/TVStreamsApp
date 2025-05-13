package com.example.tvstreamsapp.data.repositories

import android.content.Context
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.local.models.TVChannelDb
import com.example.tvstreamsapp.data.utils.mapDomainToDb
import com.example.tvstreamsapp.data.utils.mapToDomain
import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val channelsDao: ChannelsDao,
) : ChannelsRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var id = 0

    private val refreshedEvents = MutableSharedFlow<Unit>()

    private val dataFlow: Flow<List<TVChannel>> = flow {
        delay(3000)
        val channels = channelsDao.getChannels()
        if (channels.isEmpty()) {
            fillDatabase()
        }
        emit(getList())
        refreshedEvents.collect {
            emit(getList())
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList<TVChannel>().toList()
    )


    private fun fillDatabase() {
        val channels = readAssetFile(context)
        channelsDao.addChannels(channels)
    }

    private fun getList(): List<TVChannel> {
        return channelsDao.getChannels().map { it.mapToDomain() }.toList()
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

    override fun getChannels(): Flow<List<TVChannel>> {
        return dataFlow
    }

    override fun refreshList(item: TVChannel) {
        coroutineScope.launch {
            channelsDao.findActive()?.let { channelsDao.changeItem(it.changeActive()) }
            channelsDao.changeItem(item.changeActive().mapDomainToDb())
            refreshedEvents.emit(Unit)
        }
    }

    companion object {
        private const val CHANNELS_FILE = "Channels.txt"
        private const val EXTINF = "#EXTINF"
        private const val HTTP = "https"
    }

}