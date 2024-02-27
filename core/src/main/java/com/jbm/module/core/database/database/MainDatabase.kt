package com.jbm.module.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jbm.module.core.database.converter.Converters
import com.jbm.module.core.database.dao.PhraseDao
import com.jbm.module.core.database.dao.WidgetDao
import com.jbm.module.core.database.dao.WidgetStyleDao
import com.jbm.module.core.database.model.PhraseEntity
import com.jbm.module.core.database.model.WidgetEntity
import com.jbm.module.core.database.model.WidgetStyleEntity

@Database(
    version = 1,
    entities = [PhraseEntity::class, WidgetEntity::class, WidgetStyleEntity::class],
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
    abstract fun widgetDao(): WidgetDao
    abstract fun widgetStyleDao(): WidgetStyleDao
}
