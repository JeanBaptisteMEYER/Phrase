package com.jbm.phrase.ui.screen.widgetsetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.phrase.domain.repository.PhraseRepository
import com.jbm.phrase.domain.repository.WidgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPhraseViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val widgetRepository: WidgetRepository
) : ViewModel() {

    //=================================================================
    // OUTPUTS
    //=================================================================

    private val _uiState = MutableStateFlow<SelectPhraseUiState>(SelectPhraseUiState.Loading)
    val uiState = _uiState.asStateFlow()

    //=================================================================
    // INPUTS
    //=================================================================

    fun getAllPhrases() = viewModelScope.launch {
        val phrases = phraseRepository.getAllPhrases()
        _uiState.update {
            SelectPhraseUiState.Success(phrases = phrases)
        }
    }

    fun setupWidget(phraseId: Int) = viewModelScope.launch {
        _uiState.update { SelectPhraseUiState.Loading }
        val newWidgetId = widgetRepository.createNewWidget(phraseId.toString())

        _uiState.update {
            SelectPhraseUiState.WidgetSetupSuccess(widgetId = newWidgetId)
        }
    }
}
