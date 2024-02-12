package com.jbm.phrase.domain.repository

import com.jbm.phrase.domain.model.WidgetDomain

interface WidgetRepository {
    suspend fun getAllWidgets(): List<WidgetDomain>
    suspend fun getWidgetById(id: String): Result<WidgetDomain>
    suspend fun getPhraseIdByWidgetId(id: String): Result<String>
    suspend fun createNewWidget(phraseId: Int, textFont: String, textSize: Int, textColor: Int): Int
}
