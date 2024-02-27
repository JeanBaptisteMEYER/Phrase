package com.jbm.phrase.ui.widget.glancesingleline

import android.graphics.Typeface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.module.core.domain.GetPhraseByWidgetIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleLineWidgetViewModel @Inject constructor(
    private val getPhraseByWidgetIdUseCase: GetPhraseByWidgetIdUseCase
) : ViewModel() {

    //=================================================================
    // OUTPUTS
    //=================================================================

    private val _widgetUiState = MutableStateFlow<WidgetUIState>(WidgetUIState.Loading)
    val widgetUiState = _widgetUiState.asStateFlow()

    //=================================================================
    // INPUTS
    //=================================================================

    fun getPhrase(widgetId: String) = viewModelScope.launch {
        getPhraseByWidgetIdUseCase(widgetId)
            .onSuccess { widget ->
                _widgetUiState.update {
                    WidgetUIState.Success(widget)
                }
            }
            .onFailure { throwable ->
                _widgetUiState.update {
                    WidgetUIState.Error(throwable.message)
                }
            }
    }

    fun updateFontTypeFace(typeface: Typeface?) {
        (widgetUiState.value as? WidgetUIState.Success)?.let { uiState ->
            _widgetUiState.update { uiState.copy(typeface = typeface) }
        }
    }
}
/*
       (uiState.value as? WidgetSetupUiState.WidgetSetup)?.let { state ->
            _uiState.update { state.copy(textFontName = state.fontList.random()) }
        }

            LocalContext.current.getDownloadableFont(
                state.widget.textFontName,
                object : FontsContractCompat.FontRequestCallback() {

                    override fun onTypefaceRetrieved(typeface: Typeface) {

                    }

                    override fun onTypefaceRequestFailed(reason: Int) {

                    }
                })
                    val request = FontRequest(
        "com.google.android.gms.fonts",
        "com.google.android.gms",
        fontName,
        R.array.com_google_android_gms_fonts_certs
    )
    FontsContractCompat.requestFont(this, request, callback, Handler())
 */
