package com.jbm.phrase.ui.screen.detail

import com.jbm.module.core.data.model.PhraseDomain

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data object Error : DetailUiState
    data class Success(val phrase: PhraseDomain, val suggestion: List<String>) : DetailUiState
}
