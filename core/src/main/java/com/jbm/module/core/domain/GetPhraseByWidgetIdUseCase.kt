package com.jbm.module.core.domain

import com.jbm.module.core.data.model.WidgetDomain
import com.jbm.module.core.data.repository.WidgetRepository
import javax.inject.Inject

class GetPhraseByWidgetIdUseCase @Inject constructor(
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(widgetId: String): Result<WidgetDomain> {
        return widgetRepository.getWidgetById(widgetId)
    }
}
