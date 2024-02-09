package com.jbm.phrase.ui.screen.widgetsetup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jbm.phrase.domain.model.PhraseDomain

@Composable
fun SelectPhraseDestination(
    onConfigurationFinished: (Int) -> Unit = {},
    viewModel: SelectPhraseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllPhrases()
    }

    if (uiState is SelectPhraseUiState.WidgetSetupSuccess) {
        onConfigurationFinished((uiState as SelectPhraseUiState.WidgetSetupSuccess).widgetId)
    }

    SelectPhraseScreen(
        viewModel::setupWidget,
        uiState = uiState
    )
}

@Composable
fun SelectPhraseScreen(
    onPhraseItemClicked: (Int) -> Unit = {},
    uiState: SelectPhraseUiState
) {
    if (uiState is SelectPhraseUiState.Success) {
        LazyColumn {
            items(uiState.phrases) { phrase ->
                PhraseListItem(
                    onPhraseItemClicked = onPhraseItemClicked,
                    phraseDomain = phrase
                )
            }
        }
    }
}

@Composable
fun PhraseListItem(
    onPhraseItemClicked: (Int) -> Unit = {},
    phraseDomain: PhraseDomain
) {
    Row {
        Text(
            text = phraseDomain.phrase,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onPhraseItemClicked(phraseDomain.id)
                }
        )
    }
}
