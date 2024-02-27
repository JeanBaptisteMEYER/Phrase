package com.jbm.module.core.data.repository

import com.jbm.module.core.data.di.DispatcherIO
import com.jbm.module.core.data.model.WidgetDomain
import com.jbm.module.core.database.dao.PhraseDao
import com.jbm.module.core.database.dao.WidgetDao
import com.jbm.module.core.database.dao.WidgetStyleDao
import com.jbm.module.core.database.model.WidgetEntity
import com.jbm.module.core.database.model.WidgetStyleEntity
import com.jbm.module.core.database.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val widgetDao: WidgetDao,
    private val widgetStyleDao: WidgetStyleDao,
    private val phraseDao: PhraseDao,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : WidgetRepository {
    override suspend fun getAllWidgets(): List<WidgetDomain> {
        return withContext(dispatcherIO) {
            widgetDao.getAllWidgets().map { widget ->
                val widgetStyle = widgetStyleDao.getWidgetStyleById(widget.widgetStyleId)
                val phrase = phraseDao.getPhraseById(widget.phraseId)

                widget.toDomain().copy(
                    phrase = phrase?.phrase ?: "",
                    textFontName = widgetStyle?.textFontName ?: "",
                    textSize = widgetStyle?.textSize ?: -1,
                    textColor = widgetStyle?.textColor ?: -1
                )
            }
        }
    }

    override suspend fun getWidgetById(id: String): Result<WidgetDomain> {
        return withContext(dispatcherIO) {
            widgetDao.getWidgetById(id)?.let { widget ->
                val widgetStyle = widgetStyleDao.getWidgetStyleById(widget.widgetStyleId)
                val phrase = phraseDao.getPhraseById(widget.phraseId)

                widget.toDomain().copy(
                    phrase = phrase?.phrase ?: "",
                    textFontName = widgetStyle?.textFontName ?: "",
                    textSize = widgetStyle?.textSize ?: -1,
                    textColor = widgetStyle?.textColor ?: -1
                )
            }?.let {
                Result.success(it)
            } ?: run {
                Result.failure(Exception("404 - Widget Not Found"))
            }
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

    override suspend fun createNewWidget(
        phraseId: Int,
        textFont: String,
        textSize: Int,
        textColor: Int
    ): Int {
        return withContext(dispatcherIO) {
            val widgetStyle = WidgetStyleEntity(
                textFontName = textFont,
                textSize = textSize,
                textColor = textColor,
                widgetBackgroundColor = -1,
                widgetFrame = ""
            )
            val widgetStyleId = widgetStyleDao.insertWidgetStyle(widgetStyle).toInt().toString()
            val widget = WidgetEntity(
                phraseId = phraseId.toString(),
                widgetStyleId = widgetStyleId
            )
            widgetDao.insertWidget(widget).toInt()
        }
    }
}
