package com.jbm.phrase.ui.widget.glancesingleline

import android.content.Context
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.provider.FontsContractCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentHeight
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.jbm.phrase.WIDGET_ID_STATE_KEY
import com.jbm.phrase.WidgetConfigurationActivity
import com.jbm.phrase.di.SingleLineWidgetEntryPoint
import com.jbm.phrase.extention.getDownloadableFont
import com.jbm.phrase.extention.textAsBitmap
import com.jbm.phrase.ui.widget.WidgetTheme
import dagger.hilt.EntryPoints

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
            val widgetId = (prefs[intPreferencesKey(WIDGET_ID_STATE_KEY)] ?: -1)

            val uiState = viewModel.widgetUiState.collectAsState()
            viewModel.getPhrase(widgetId.toString())

            // View
            WidgetTheme {
                SingleLineContent(
                    onTypefaceRetrievedCallback = viewModel::updateFontTypeFace,
                    widgetId = widgetId,
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
fun SingleLineContent(
    onTypefaceRetrievedCallback: (Typeface?) -> Unit,
    widgetId: Int,
    uiState: State<WidgetUIState>
) {
    when (val data = uiState.value) {
        is WidgetUIState.Loading -> {
            CircularProgressIndicator()
        }

        is WidgetUIState.Error -> {
            Text(text = "Oups... Something's wrong")
        }

        is WidgetUIState.Success -> {
            Row(
                modifier = GlanceModifier
                    .background(GlanceTheme.colors.background)
            ) {
                GlanceText(
                    modifier = GlanceModifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            actionStartActivity<WidgetConfigurationActivity>(
                                /* Not needed for now. See widget reconfigurable feature
                                actionParametersOf(
                                    ActionParameters.Key<String>(SINGLE_LINE_ACTION_PARAM_KEY) to widgetId
                                )
                                 */
                            )
                        ),
                    onTypefaceRetrievedCallback = onTypefaceRetrievedCallback,
                    uiState = data
                )
            }
        }
    }
}

@Composable
fun GlanceText(
    modifier: GlanceModifier,
    onTypefaceRetrievedCallback: (Typeface?) -> Unit,
    uiState: WidgetUIState.Success
) {
    val localContext = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        localContext.getDownloadableFont(
            uiState.widget.textFontName,
            object : FontsContractCompat.FontRequestCallback() {
                override fun onTypefaceRetrieved(typeface: Typeface?) {
                    super.onTypefaceRetrieved(typeface)
                    onTypefaceRetrievedCallback(typeface)
                }
            }
        )
    }

    Image(
        modifier = modifier,
        provider = ImageProvider(
            localContext.textAsBitmap(
                text = uiState.widget.phrase,
                fontSize = uiState.widget.textSize,
                color = uiState.widget.textColor,
                typeFace = uiState.typeface,
                letterSpacing = 0.1.sp.value
            )
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}
