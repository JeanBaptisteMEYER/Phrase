package com.jbm.phrase.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.phrase.domain.FetchAllPhraseUseCase
import com.jbm.phrase.domain.SavePhraseUseCase
import com.jbm.phrase.ui.screen.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val savePhraseUseCase: SavePhraseUseCase,
    private val fetchAllPhraseUseCase: FetchAllPhraseUseCase
): ViewModel() {
    
    //=================================================================
    // OUTPUTS
    //=================================================================
    
    val phraseInput = savedStateHandle.getStateFlow(key = PHRASE_INPUT, initialValue = "")
    
    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()
    
    //=================================================================
    // INPUTS
    //=================================================================
    
    fun getAllPhrase() = viewModelScope.launch {
        val all = fetchAllPhraseUseCase()
        _homeUiState.update { HomeUiState.ListReady(all.map { it.phrase }) }
    }
    
    fun onPhraseInputChanged(phrase: String) {
        savedStateHandle[PHRASE_INPUT] = phrase
    }
    
    fun onPhraseSavedTriggered(phrase: String) = viewModelScope.launch {
        savePhraseUseCase(phrase)
        savedStateHandle[PHRASE_INPUT] = ""
        getAllPhrase()
    }
}

private const val PHRASE_INPUT = "phraseInput"
