package com.jbm.phrase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "widget_style")
data class WidgetStyleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val textFontName: String,
    val textSize: Int,
    val textColor: Int,
    val widgetBackgroundColor: Int,
    val widgetFrame: String,
    val createdAd: Date = Date()
)
