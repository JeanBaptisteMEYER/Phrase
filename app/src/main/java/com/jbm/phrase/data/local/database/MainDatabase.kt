package com.jbm.phrase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jbm.phrase.data.local.converter.Converters
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.dao.WidgetDao
import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.data.local.entity.WidgetEntity

@Database(
    version = 1,
    entities = [PhraseEntity::class, WidgetEntity::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
    abstract fun widgetDao(): WidgetDao
}
