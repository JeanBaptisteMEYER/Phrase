package com.jbm.phrase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class PhraseEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phrase: String
)
