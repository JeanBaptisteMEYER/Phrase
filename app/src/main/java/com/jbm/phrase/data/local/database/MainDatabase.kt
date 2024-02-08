package com.jbm.phrase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jbm.phrase.data.local.converter.Converters
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.entity.PhraseEntity

@Database(
    version = 1,
    entities = [PhraseEntity::class]
)
@TypeConverters(Converters::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
}
