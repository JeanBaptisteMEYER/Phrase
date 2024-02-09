package com.jbm.phrase.data.repository

import com.jbm.phrase.data.local.dao.WidgetDao
import com.jbm.phrase.data.local.entity.WidgetEntity
import com.jbm.phrase.data.local.entity.toDomain
import com.jbm.phrase.di.DispatcherIO
import com.jbm.phrase.domain.model.WidgetDomain
import com.jbm.phrase.domain.repository.WidgetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val widgetDao: WidgetDao,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : WidgetRepository {
    override suspend fun getAllWidgets(): List<WidgetDomain> {
        return withContext(dispatcherIO) {
            widgetDao.getAllWidgets().toDomain()
        }
    }

    override suspend fun getWidgetById(id: String): WidgetDomain? {
        return withContext(dispatcherIO) {
            widgetDao.getWidgetById(id)?.toDomain()
        }
    }

    override suspend fun getPhraseIdByWidgetId(id: String): Result<String> {
        return withContext(dispatcherIO) {
            widgetDao.getPhraseIdByWidgetId(id)?.let { phrasesId ->
                Result.success(phrasesId)
            } ?: run {
                Result.failure(Exception("404 - PhraseId Not Found"))
            }
        }
    }

    override suspend fun createNewWidget(phraseId: String): Int {
        return withContext(dispatcherIO) {
            val widget = WidgetEntity(phraseId = phraseId, createdAt = Date())
            widgetDao.insertWidget(widget).toInt()
        }
    }
}
