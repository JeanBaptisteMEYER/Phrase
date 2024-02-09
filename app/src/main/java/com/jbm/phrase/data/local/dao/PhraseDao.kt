package com.jbm.phrase.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jbm.phrase.data.local.entity.PhraseEntity

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
