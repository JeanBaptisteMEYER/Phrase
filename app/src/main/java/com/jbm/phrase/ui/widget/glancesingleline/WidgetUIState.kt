package com.jbm.phrase.ui.widget.glancesingleline

import android.graphics.Typeface
import com.jbm.module.core.data.model.WidgetDomain

sealed interface WidgetUIState {
    data object Loading : WidgetUIState
    data class Success(val widget: WidgetDomain, val typeface: Typeface? = null) : WidgetUIState
    data class Error(val error: String?) : WidgetUIState
}
