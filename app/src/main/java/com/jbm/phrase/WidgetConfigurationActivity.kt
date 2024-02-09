package com.jbm.phrase

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.lifecycleScope
import com.jbm.phrase.ui.screen.widgetsetup.SelectPhraseDestination
import com.jbm.phrase.ui.theme.PhraseTheme
import com.jbm.phrase.ui.widget.singleline.SINGLE_LINE_ID_PARAM_KEY
import com.jbm.phrase.ui.widget.singleline.SingleLineWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WidgetConfigurationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Activity for widget
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_CANCELED, resultValue)

        setContent {
            PhraseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SelectPhraseDestination(
                        onConfigurationFinished = { widgetId ->
                            updateWidgetAndFinish(appWidgetId, widgetId)
                        }
                    )
                }
            }
        }
    }

    private fun updateWidgetAndFinish(appWidgetId: Int, widgetId: Int) = lifecycleScope.launch {
        val glanceAppWidgetManager = GlanceAppWidgetManager(this@WidgetConfigurationActivity)
        val glanceId: GlanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)

        updateAppWidgetState(this@WidgetConfigurationActivity, glanceId) {
            it[intPreferencesKey(SINGLE_LINE_ID_PARAM_KEY)] = widgetId
        }

        val widget = SingleLineWidget()
        widget.update(this@WidgetConfigurationActivity, glanceId)

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}
