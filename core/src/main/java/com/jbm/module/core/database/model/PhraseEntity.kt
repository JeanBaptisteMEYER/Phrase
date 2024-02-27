package com.jbm.module.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jbm.module.core.data.model.PhraseDomain
import java.util.Date

@Entity(tableName = "phrase")
data class PhraseEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phrase: String,
    val createdAt: Date
)

fun PhraseEntity.toDomain() =
    PhraseDomain(id = this.id, phrase = this.phrase, createdAt = this.createdAt)

fun List<PhraseEntity>.toDomain() = map { it.toDomain() }
