package com.jbm.module.core.data.model

import com.jbm.module.core.database.model.PhraseEntity
import java.util.Date

data class PhraseDomain(
    val id: Int,
    val phrase: String,
    val createdAt: Date = Date()
)

fun PhraseDomain.toEntity(): PhraseEntity =
    PhraseEntity(phrase = this.phrase, createdAt = this.createdAt)
