package com.jbm.phrase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jbm.phrase.domain.model.WidgetDomain
import java.util.Date

@Entity(tableName = "widget")
data class WidgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phraseId: String,
    val createdAt: Date
)

fun WidgetEntity.toDomain() =
    WidgetDomain(widgetId = this.id, phraseId = this.phraseId, createdAt = this.createdAt)

fun List<WidgetEntity>.toDomain() = map { it.toDomain() }
