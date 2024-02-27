package com.jbm.module.core.data.repository

import com.jbm.module.core.data.model.WidgetDomain

interface WidgetRepository {
    suspend fun getAllWidgets(): List<WidgetDomain>
    suspend fun getWidgetById(id: String): Result<WidgetDomain>
    suspend fun getPhraseIdByWidgetId(id: String): Result<String>
    suspend fun createNewWidget(phraseId: Int, textFont: String, textSize: Int, textColor: Int): Int
}
