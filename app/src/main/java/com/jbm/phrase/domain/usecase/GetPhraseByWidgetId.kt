package com.jbm.phrase.domain.usecase

import com.jbm.phrase.domain.model.PhraseDomain
import com.jbm.phrase.domain.repository.PhraseRepository
import com.jbm.phrase.domain.repository.WidgetRepository
import javax.inject.Inject

class GetPhraseByWidgetId @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(widgetId: String): Result<PhraseDomain> {
        return widgetRepository.getPhraseIdByWidgetId(widgetId)
            .mapCatching { phraseId ->
                phraseRepository.getPhraseById(phraseId).getOrThrow()
            }
    }
}
