package com.jbm.module.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jbm.module.core.database.model.PhraseEntity

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrase")
    fun getAllPhrase(): List<PhraseEntity>

    @Query("SELECT * FROM phrase WHERE id=:id")
    fun getPhraseById(id: String): PhraseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhrase(phraseEntity: PhraseEntity)

    @Delete
    fun deletePhrase(phraseEntity: PhraseEntity)
}
