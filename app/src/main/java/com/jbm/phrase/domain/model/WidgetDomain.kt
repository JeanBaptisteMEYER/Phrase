package com.jbm.phrase.domain.model

import com.jbm.phrase.data.local.entity.WidgetEntity
import java.util.Date

data class WidgetDomain(
    val widgetId: Int = -1,
    val phraseId: String,
    val createdAt: Date
)

fun WidgetDomain.toEntity(): WidgetEntity =
    WidgetEntity(phraseId = this.phraseId, createdAt = this.createdAt)
