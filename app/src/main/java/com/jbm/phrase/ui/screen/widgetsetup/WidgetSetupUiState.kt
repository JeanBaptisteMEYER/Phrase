package com.jbm.phrase.ui.screen.widgetsetup

import android.graphics.Color
import com.jbm.phrase.domain.model.PhraseDomain

sealed interface WidgetSetupUiState {
    data object Loading : WidgetSetupUiState
    data object Error : WidgetSetupUiState
    data class WidgetSetup(
        val phrases: List<PhraseDomain>,
        val textFontName: String = "Roboto",
        val textSize: Int = 22,
        val textColor: Int = Color.BLACK,
        val backgroundColor: Int = Color.WHITE,
        val fontList: List<String> = listOf()
    ) : WidgetSetupUiState
    data class WidgetSetupSuccess(val widgetId: Int) : WidgetSetupUiState
}
