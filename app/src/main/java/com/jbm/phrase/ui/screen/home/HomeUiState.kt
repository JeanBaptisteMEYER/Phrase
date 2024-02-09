package com.jbm.phrase.ui.screen.home

import com.jbm.phrase.domain.model.PhraseDomain

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val phrases: List<PhraseDomain>) : HomeUiState
}
