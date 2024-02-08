package com.jbm.phrase.ui.screen.detail

import com.jbm.phrase.domain.model.PhraseDomain

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data object Error : DetailUiState
    data class Success(val phase: PhraseDomain, val suggestion: List<String>) : DetailUiState
}
