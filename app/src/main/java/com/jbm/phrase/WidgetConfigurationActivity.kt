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
import com.jbm.phrase.ui.screen.widgetsetup.WidgetSetupDestination
import com.jbm.phrase.ui.theme.PhraseTheme
import com.jbm.phrase.ui.widget.glancesingleline.SingleLineWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val WIDGET_ID_ACTION_PARAM_KEY = "WIDGET_ID_ACTION_PARAM_KEY"
const val WIDGET_ID_STATE_KEY = "WIDGET_ID_STATE_KEY"
const val INVALID_WIDGET_ID = -1

@AndroidEntryPoint
class WidgetConfigurationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Activity for widget
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            INVALID_WIDGET_ID
        ) ?: INVALID_WIDGET_ID

        // Set the result to CANCELED. This will cause the widget host to cancel out of the widget
        // placement if they press the back button.
        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_CANCELED, resultValue)

        /* Not needed for now. See widget reconfigurable
        val stableWidgetId = intent?.extras?.getInt(
            WIDGET_ID_ACTION_PARAM_KEY,
            INVALID_WIDGET_ID
        ) ?: INVALID_WIDGET_ID
         */

        setContent {
            PhraseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WidgetSetupDestination(
                        onConfigurationFinished = { appWidgetId, widgetId ->
                            updateWidgetAndFinish(appWidgetId, widgetId)
                        },
                        appWidgetId = appWidgetId
                    )
                }
            }
        }
    }

    private fun updateWidgetAndFinish(appWidgetId: Int, stableWidgetId: Int) =
        lifecycleScope.launch {
            val widget = SingleLineWidget()
            val glanceAppWidgetManager = GlanceAppWidgetManager(this@WidgetConfigurationActivity)
            val glanceId: GlanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)

            updateAppWidgetState(this@WidgetConfigurationActivity, glanceId) {
                it[intPreferencesKey(WIDGET_ID_STATE_KEY)] = stableWidgetId
            }

            widget.update(this@WidgetConfigurationActivity, glanceId)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(Activity.RESULT_OK, resultValue)

            finish()
    }
}
