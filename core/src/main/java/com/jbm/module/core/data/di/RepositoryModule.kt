package com.jbm.module.core.data.di

import android.content.res.AssetManager
import com.google.gson.Gson
import com.jbm.module.core.data.repository.AssetRepository
import com.jbm.module.core.data.repository.AssetRepositoryImpl
import com.jbm.module.core.data.repository.PhraseRepository
import com.jbm.module.core.data.repository.PhraseRepositoryImpl
import com.jbm.module.core.data.repository.WidgetRepository
import com.jbm.module.core.data.repository.WidgetRepositoryImpl
import com.jbm.module.core.database.dao.PhraseDao
import com.jbm.module.core.database.dao.WidgetDao
import com.jbm.module.core.database.dao.WidgetStyleDao
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
