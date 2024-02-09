package com.jbm.phrase.domain.model

import com.jbm.phrase.data.local.entity.PhraseEntity
import java.util.Date

data class PhraseDomain (
    val id: Int,
    val phrase: String,
    val createdAt: Date
)

fun PhraseDomain.toEntity(): PhraseEntity =
    PhraseEntity(phrase = this.phrase, createdAt = this.createdAt)
