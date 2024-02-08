package com.jbm.phrase.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
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
import com.jbm.phrase.domain.model.PhraseDomain
import java.util.Date

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
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
        onPhraseItemClicked = onDetailClick,
        phraseInput = phraseInput,
        uiState = uiState
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onPhraseInputChanged: (String) -> Unit = {},
    onPhraseSavedTriggered: (String) -> Unit = {},
    onPhraseItemClicked: (String) -> Unit = {},
    phraseInput: String,
    uiState: HomeUiState
) {

    when (uiState) {
        is HomeUiState.Success -> {
            Column(modifier = modifier) {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
                PhraseInput(
                    onPhraseInputChanged = onPhraseInputChanged,
                    onPhraseSavedTriggered = onPhraseSavedTriggered,
                    phraseInput = phraseInput,
                )
                PhraseList(
                    onPhraseItemClicked = onPhraseItemClicked,
                    uiState = uiState
                )
            }
        }

        HomeUiState.Error -> {
            Text(text = "Error")
        }

        HomeUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
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
            onDone = {
                onPhraseSavedTriggered(phraseInput)
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
    onPhraseItemClicked: (String) -> Unit = {},
    uiState: HomeUiState
) {
    if (uiState is HomeUiState.Success) {
        LazyColumn {
            items(uiState.phases) { phrase ->
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
    onPhraseItemClicked: (String) -> Unit = {},
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
                    onPhraseItemClicked(phraseDomain.id.toString())
                }
        )
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
        uiState = HomeUiState.Success(
            listOf(
                PhraseDomain(1, "Hello", Date()),
                PhraseDomain(2, "Hello this is a phrase", Date()),
                PhraseDomain(3, "Hello another one", Date())
            )
        )
    )
}

