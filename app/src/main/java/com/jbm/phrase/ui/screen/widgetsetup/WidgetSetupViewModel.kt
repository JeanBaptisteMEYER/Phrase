package com.jbm.phrase.ui.screen.widgetsetup

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.module.core.data.repository.AssetRepository
import com.jbm.module.core.data.repository.PhraseRepository
import com.jbm.module.core.data.repository.WidgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class WidgetSetupViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val widgetRepository: WidgetRepository,
    private val assetRepository: AssetRepository
) : ViewModel() {

    companion object {
        const val TEXT_SIZE_STEP: Int = 2
    }

    //=================================================================
    // OUTPUTS
    //=================================================================

    private val _uiState = MutableStateFlow<WidgetSetupUiState>(WidgetSetupUiState.Loading)
    val uiState = _uiState.asStateFlow()

    //=================================================================
    // INPUTS
    //=================================================================

    fun loadData() = viewModelScope.launch {


        val test = widgetRepository.getAllWidgets()
        Log.d("coucou", "loadData: $test")

        combine(
            flowOf(phraseRepository.getAllPhrases()),
            flowOf(assetRepository.getAllGoogleFont())
        ) { phrases, fonts ->
            Pair(phrases, fonts)
        }.collect { result ->
            val (phrases, fonts) = result

            val fontNameList = fonts.getOrDefault(listOf()).map { it.family }

            updateState(
                WidgetSetupUiState.WidgetSetup(
                    phrases = phrases,
                    textFontName = fontNameList.random(),
                    fontList = fontNameList
                )
            )
        }
    }

    fun setupNewWidget(phraseId: Int, textFont: String, textSize: Int, textColor: Int) =
        viewModelScope.launch {
            updateState(WidgetSetupUiState.Loading)
            val newWidgetId =
                widgetRepository.createNewWidget(phraseId, textFont, textSize, textColor)
            updateState(WidgetSetupUiState.WidgetSetupSuccess(widgetId = newWidgetId))
        }

    fun onTextFontClicked() = viewModelScope.launch {
        (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(textFontName = state.fontList.random()) }
        }
    }

    fun onDecreaseTextSizeClicked() = viewModelScope.launch {
        (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(textSize = state.textSize - TEXT_SIZE_STEP) }
        }
    }

    fun onIncreaseTextSizeClicked() = viewModelScope.launch {
        (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(textSize = state.textSize + TEXT_SIZE_STEP) }
        }
    }

    fun onTextColorClicked() = viewModelScope.launch {
        val color = Color.argb(
            255,
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        )

        (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(textColor = color) }
        }
    }

    fun onBackgroundColorClicked() = viewModelScope.launch {
        val color = Color.argb(
            Random.nextInt(155, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        )

        (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(backgroundColor = color) }
        }
    }

    private fun updateState(newState: WidgetSetupUiState) {
        _uiState.update { newState }
    }
}
