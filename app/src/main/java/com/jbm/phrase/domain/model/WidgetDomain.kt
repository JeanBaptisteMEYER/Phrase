package com.jbm.phrase.domain.model

import java.util.Date

data class WidgetDomain(
    val phrase: String,
    val widgetStyleId: String,
    val textFontName: String,
    val textSize: Int,
    val textColor: Int,
    val widgetBackgroundColor: Int,
    val widgetFrame: String,
    val createdAt: Date
)
