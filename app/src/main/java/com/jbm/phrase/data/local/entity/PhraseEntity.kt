package com.jbm.phrase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jbm.phrase.domain.model.PhraseDomain
import java.util.Date

@Entity(tableName = "phrases")
data class PhraseEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phrase: String,
    val lastAdded: Date
)

fun PhraseEntity.toDomain() =
    PhraseDomain(id = this.id, phrase = this.phrase, lastAdded = this.lastAdded)

fun List<PhraseEntity>.toDomain() = map { it.toDomain() }
