package com.jbm.phrase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.jbm.phrase.navigation.PhraseNavHost
import com.jbm.phrase.ui.theme.PhraseTheme
import com.jbm.phrase.ui.widget.glancesingleline.SingleLineWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhraseTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhraseNavHost(navController = navController)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // This is needed during development to update widget after every compilation
        // It updates every Phrase widget
        lifecycleScope.launch {
            val manager = GlanceAppWidgetManager(this@MainActivity)
            val widget = SingleLineWidget()
            val glanceIds = manager.getGlanceIds(widget.javaClass)
            glanceIds.forEach { glanceId ->
                widget.update(this@MainActivity, glanceId)
            }
        }
    }
}
