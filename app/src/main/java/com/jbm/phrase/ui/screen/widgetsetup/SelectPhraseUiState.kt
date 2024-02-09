package com.jbm.phrase.ui.screen.widgetsetup

import com.jbm.phrase.domain.model.PhraseDomain

sealed interface SelectPhraseUiState {
    data object Loading : SelectPhraseUiState
    data object Error : SelectPhraseUiState
    data class Success(val phrases: List<PhraseDomain>) : SelectPhraseUiState
    data class WidgetSetupSuccess(val widgetId: Int) : SelectPhraseUiState
}
