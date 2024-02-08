package com.jbm.phrase.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.phrase.domain.usecase.GetPhraseByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPhraseByIdUseCase: GetPhraseByIdUseCase
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
        val phrase = getPhraseByIdUseCase(phasesId)
        val mockSuggestionList = listOf(
            "Coucou cest moi",
            "Cest encore moi",
            "Another one"
        )
        
        _detailUiState.update {
            phrase?.let {
                DetailUiState.Success(it, mockSuggestionList)
            } ?: run {
                DetailUiState.Error
            }
        }
    }
}
