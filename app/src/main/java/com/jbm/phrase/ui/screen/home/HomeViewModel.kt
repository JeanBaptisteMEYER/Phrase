package com.jbm.phrase.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.module.core.data.repository.PhraseRepository
import com.jbm.phrase.extention.trimEmptyLines
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
    private val phraseRepository: PhraseRepository
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
        val phrases = phraseRepository.getAllPhrases()
        _homeUiState.update {
            HomeUiState.Success(phrases.sortedByDescending { it.createdAt })
        }
    }

    fun onPhraseInputChanged(phrase: String) {
        savedStateHandle[PHRASE_INPUT] = phrase
    }
    
    fun onPhraseSavedTriggered(phrase: String) = viewModelScope.launch {
        phraseRepository.insertPhrase(phrase.trimEmptyLines())
        savedStateHandle[PHRASE_INPUT] = ""
        getAllPhrase()
    }
}

private const val PHRASE_INPUT = "phraseInput"
