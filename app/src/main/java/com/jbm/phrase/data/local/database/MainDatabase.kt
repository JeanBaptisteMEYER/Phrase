package com.jbm.phrase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.entity.PhraseEntity

@Database(entities = [PhraseEntity::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
}
