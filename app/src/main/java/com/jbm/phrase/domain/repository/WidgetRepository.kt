package com.jbm.phrase.domain.repository

import com.jbm.phrase.domain.model.WidgetDomain

interface WidgetRepository {
    suspend fun getAllWidgets(): List<WidgetDomain>
    suspend fun getWidgetById(id: String): WidgetDomain?
    suspend fun getPhraseIdByWidgetId(id: String): Result<String>
    suspend fun createNewWidget(phraseId: String): Int
}
