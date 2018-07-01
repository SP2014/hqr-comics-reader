package com.tiagohs.hqr.dragger.modules

import android.content.Context
import com.tiagohs.hqr.database.ISourceRepository
import com.tiagohs.hqr.download.*
import com.tiagohs.hqr.helpers.tools.PreferenceHelper
import com.tiagohs.hqr.notification.DownloadNotification
import com.tiagohs.hqr.sources.SourceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DownloadModule {

    @Provides
    fun providerDownloadProvider(context: Context, preferences: PreferenceHelper): DownloadProvider {
        return DownloadProvider(context, preferences)
    }

    @Provides
    fun providerDownloadCache(context: Context, sourceManager: SourceManager, provider: DownloadProvider, preferences: PreferenceHelper, preferenceHelper: PreferenceHelper, sourceRepository: ISourceRepository): DownloadCache {
        return DownloadCache(context, sourceManager, provider, preferences, sourceRepository, preferenceHelper)
    }

    @Singleton
    @Provides
    fun providerDownloader(context: Context, provider: DownloadProvider, sourceManager: SourceManager, store: DownloadStore, cache: DownloadCache, downloadNotification: DownloadNotification, preferenceHelper: PreferenceHelper, sourceRepository: ISourceRepository): Downloader {
        return Downloader(context, provider, sourceManager, store, cache, downloadNotification, sourceRepository, preferenceHelper)
    }

    @Singleton
    @Provides
    fun providerDownlaoderManager(downloader: Downloader, cache: DownloadCache, provider: DownloadProvider): DownloadManager {
        return DownloadManager(downloader, cache, provider)
    }

    @Provides
    fun providerDownloadStore(context: Context, sourceManager: SourceManager, preferenceHelper: PreferenceHelper, sourceRepository: ISourceRepository): DownloadStore {
        return DownloadStore(context, sourceManager, sourceRepository, preferenceHelper)
    }


}