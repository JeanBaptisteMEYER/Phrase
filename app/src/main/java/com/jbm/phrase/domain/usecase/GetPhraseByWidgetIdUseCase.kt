package com.jbm.phrase.domain.usecase

import com.jbm.phrase.domain.model.WidgetDomain
import com.jbm.phrase.domain.repository.WidgetRepository
import javax.inject.Inject

class GetPhraseByWidgetIdUseCase @Inject constructor(
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(widgetId: String): Result<WidgetDomain> {
        return widgetRepository.getWidgetById(widgetId)
    }
}
