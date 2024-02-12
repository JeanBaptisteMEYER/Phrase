package com.jbm.phrase.di

import android.content.res.AssetManager
import com.google.gson.Gson
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.dao.WidgetDao
import com.jbm.phrase.data.local.dao.WidgetStyleDao
import com.jbm.phrase.data.repository.AssetRepositoryImpl
import com.jbm.phrase.data.repository.PhraseRepositoryImpl
import com.jbm.phrase.data.repository.WidgetRepositoryImpl
import com.jbm.phrase.domain.repository.AssetRepository
import com.jbm.phrase.domain.repository.PhraseRepository
import com.jbm.phrase.domain.repository.WidgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun providePhraseRepository(
        phraseDao: PhraseDao,
        @DispatcherIO dispatcherIO: CoroutineDispatcher
    ): PhraseRepository = PhraseRepositoryImpl(
        phraseDao,
        dispatcherIO
    )

    @Provides
    fun provideWidgetRepository(
        widgetDao: WidgetDao,
        widgetStyleDao: WidgetStyleDao,
        phraseDao: PhraseDao,
        @DispatcherIO dispatcherIO: CoroutineDispatcher
    ): WidgetRepository = WidgetRepositoryImpl(
        widgetDao,
        widgetStyleDao,
        phraseDao,
        dispatcherIO
    )

    @Provides
    fun provideAssetRepository(
        gson: Gson,
        assets: AssetManager,
        @DispatcherIO dispatcherIO: CoroutineDispatcher
    ): AssetRepository = AssetRepositoryImpl(
        gson,
        assets,
        dispatcherIO
    )
}
