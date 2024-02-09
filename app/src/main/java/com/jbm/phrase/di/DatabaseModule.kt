package com.jbm.phrase.di

import android.content.Context
import androidx.room.Room
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.dao.WidgetDao
import com.jbm.phrase.data.local.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "PhraseMainDatabase"
        ).build()
    }

    @Provides
    fun providePhraseDao(db: MainDatabase): PhraseDao {
        return db.phraseDao()
    }

    @Provides
    fun provideWidgetDao(db: MainDatabase): WidgetDao {
        return db.widgetDao()
    }
}
