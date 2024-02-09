package com.jbm.phrase.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.phrase.domain.repository.PhraseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository
) : ViewModel() {

    //=================================================================
    // OUTPUTS
    //=================================================================

    private val _detailUiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val homeUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    //=================================================================
    // INPUTS
    //=================================================================

    fun getPhrase(phasesId: String) = viewModelScope.launch {
        val mockSuggestionList = listOf(
            "Cest moi",
            "Cest encore moi",
            "Another one"
        )

        phraseRepository.getPhraseById(phasesId)
            .onSuccess { phrase ->
                _detailUiState.update {
                    DetailUiState.Success(phrase, mockSuggestionList)
                }
            }
            .onFailure {
                _detailUiState.update { DetailUiState.Error }
            }
    }
}
