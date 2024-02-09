package com.jbm.phrase.ui.widget.singleline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.phrase.domain.usecase.GetPhraseByWidgetId
import com.jbm.phrase.ui.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleLineWidgetViewModel @Inject constructor(
    private val getPhraseByWidgetId: GetPhraseByWidgetId
) : ViewModel() {

    //=================================================================
    // OUTPUTS
    //=================================================================

    private val _widgetUiState = MutableStateFlow<UIState<String>>(UIState.Loading)
    val widgetUiState = _widgetUiState.asStateFlow()

    //=================================================================
    // INPUTS
    //=================================================================

    fun getPhrase(widgetId: String) = viewModelScope.launch {
        val phraseResult = getPhraseByWidgetId(widgetId)

        phraseResult
            .onSuccess { phrase ->
                _widgetUiState.update {
                    UIState.Success(phrase.phrase)
                }
            }
            .onFailure { throwable ->
                _widgetUiState.update {
                    UIState.Error(throwable.message)
                }
            }
    }
}
