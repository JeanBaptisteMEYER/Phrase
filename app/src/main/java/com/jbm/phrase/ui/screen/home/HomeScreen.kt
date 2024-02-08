package com.jbm.hellocompose.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jbm.phrase.R
import com.jbm.phrase.extention.trimEmptyLines
import com.jbm.phrase.ui.screen.home.HomeViewModel
import com.jbm.phrase.ui.screen.home.model.HomeUiState

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val phraseInput by viewModel.phraseInput.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.getAllPhrase()
    }
    
    HomeScreen(
        modifier,
        onPhraseInputChanged = viewModel::onPhraseInputChanged,
        onPhraseSavedTriggered = viewModel::onPhraseSavedTriggered,
        phraseInput = phraseInput,
        uiState = uiState
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onPhraseInputChanged: (String) -> Unit = {},
    onPhraseSavedTriggered: (String) -> Unit = {},
    phraseInput: String,
    uiState: HomeUiState
) {
    
    Column(modifier = modifier) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        PhraseInput(
            onPhraseInputChanged = onPhraseInputChanged,
            onPhraseSavedTriggered = onPhraseSavedTriggered,
            phraseInput = phraseInput,
        )
        PhraseList(
            uiState = uiState
        )
    }
}

@Composable
private fun PhraseInput(
    onPhraseInputChanged: (String) -> Unit,
    onPhraseSavedTriggered: (String) -> Unit,
    phraseInput: String
) {
    val focusRequester = remember { FocusRequester() }
    
    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (phraseInput.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onPhraseInputChanged("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(
                            id = R.string.home_phrase_input_clear_content_desc,
                        ),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) onPhraseInputChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onPhraseSavedTriggered(phraseInput)
                    true
                } else {
                    false
                }
            },
        value = phraseInput,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onPhraseSavedTriggered(phraseInput.trimEmptyLines())
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun PhraseList(
    modifier: Modifier = Modifier,
    onPhraseItemClicked: (String) -> Unit = {},
    uiState: HomeUiState
    )
{
    if (uiState is HomeUiState.ListReady) {
        LazyColumn {
            items(uiState.phases) { phrase ->
                Text(
                    text = phrase,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhraseInputPreview() {
    PhraseInput(
        onPhraseInputChanged = { },
        onPhraseSavedTriggered = { },
        phraseInput = "coucou"
    )
}

@Preview(showBackground = true)
@Composable
fun PhraseListPreview() {
    PhraseList(
        uiState = HomeUiState.ListReady(listOf("coucou", "coucou2", "coucou3", "coucou3", "coucou3"))
    )
}

