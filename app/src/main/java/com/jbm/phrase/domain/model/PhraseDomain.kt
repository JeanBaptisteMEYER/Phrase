package com.jbm.phrase.domain.model

import com.jbm.phrase.data.local.entity.PhraseEntity
import java.util.Date

data class PhraseDomain (
    val id: Int,
    val phrase: String,
    val lastAdded: Date
)

fun PhraseDomain.toEntity() : PhraseEntity = PhraseEntity(phrase = this.phrase, lastAdded = this.lastAdded)
