package com.jbm.phrase.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jbm.phrase.data.local.converter.Converters
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.dao.WidgetDao
import com.jbm.phrase.data.local.dao.WidgetStyleDao
import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.data.local.entity.WidgetEntity
import com.jbm.phrase.data.local.entity.WidgetStyleEntity

@Database(
    version = 2,
    entities = [PhraseEntity::class, WidgetEntity::class, WidgetStyleEntity::class],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]

)
@TypeConverters(Converters::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
    abstract fun widgetDao(): WidgetDao
    abstract fun widgetStyleDao(): WidgetStyleDao
}
