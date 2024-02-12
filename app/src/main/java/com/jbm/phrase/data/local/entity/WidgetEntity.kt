package com.jbm.phrase.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jbm.phrase.domain.model.WidgetDomain
import java.util.Date

@Entity(tableName = "widget")
data class WidgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phraseId: String,
    @ColumnInfo(defaultValue = "-1") val widgetStyleId: String,
    val createdAt: Date = Date()
)

fun WidgetEntity.toDomain() =
    WidgetDomain(
        phrase = "",
        widgetStyleId = this.widgetStyleId,
        textFontName = "",
        textSize = -1,
        textColor = -1,
        widgetBackgroundColor = -1,
        widgetFrame = "",
        createdAt = this.createdAt
    )

fun List<WidgetEntity>.toDomain() = map { it.toDomain() }
