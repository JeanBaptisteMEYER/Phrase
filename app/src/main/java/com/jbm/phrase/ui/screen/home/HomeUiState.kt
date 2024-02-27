package com.jbm.phrase.ui.screen.home

import com.jbm.module.core.data.model.PhraseDomain

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val phrases: List<PhraseDomain>) : HomeUiState
}
