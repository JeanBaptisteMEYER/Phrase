package com.jbm.phrase.ui.screen.home.model

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class ListReady(val phases: List<String>) : HomeUiState
}
