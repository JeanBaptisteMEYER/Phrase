package com.jbm.phrase.ui.widget.singleline

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentHeight
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.jbm.phrase.MainActivity
import com.jbm.phrase.di.SingleLineWidgetEntryPoint
import com.jbm.phrase.ui.model.UIState
import com.jbm.phrase.ui.widget.WidgetTheme
import dagger.hilt.EntryPoints

const val SINGLE_LINE_ACTION_PARAM_KEY = "SINGLE_LINE_ACTION_PARAM_KEY"
const val SINGLE_LINE_ID_PARAM_KEY = "SINGLE_LINE_ID_PARAM_KEY"

class SingleLineWidget : GlanceAppWidget() {

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            // Data
            val applicationContext = LocalContext.current.applicationContext
            val viewModel = EntryPoints.get(
                applicationContext,
                SingleLineWidgetEntryPoint::class.java
            ).getViewModel()

            val prefs = currentState<Preferences>()
            val widgetId = (prefs[intPreferencesKey(SINGLE_LINE_ID_PARAM_KEY)] ?: -1).toString()

            val uiState = viewModel.widgetUiState.collectAsState()
            viewModel.getPhrase(widgetId)

            // View
            WidgetTheme {
                SingleLineContent(
                    widgetId = widgetId,
                    uiState = uiState
                )
            }

        }
    }
}

@Composable
fun SingleLineContent(
    widgetId: String,
    uiState: State<UIState<String>>
) {
    when (val state = uiState.value) {
        is UIState.Loading -> {
            CircularProgressIndicator()
        }

        is UIState.Error -> {
            Text(text = "Oups... Something's wrong")
        }

        is UIState.Success -> {
            Row(
                modifier = GlanceModifier
                    .background(GlanceTheme.colors.background)
            ) {
                Text(
                    text = state.data,
                    modifier = GlanceModifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            actionStartActivity<MainActivity>(
                                actionParametersOf(
                                    ActionParameters.Key<String>(SINGLE_LINE_ACTION_PARAM_KEY) to widgetId
                                )
                            )
                        )
                )
            }
        }
    }
}
