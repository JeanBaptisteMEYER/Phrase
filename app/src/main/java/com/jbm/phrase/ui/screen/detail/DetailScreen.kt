package com.jbm.phrase.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jbm.phrase.R
import com.jbm.phrase.domain.model.PhraseDomain
import java.util.Date

@Composable
fun DetailDestination(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    phraseId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPhrase(phraseId)
    }

    DetailScreen(
        onBackClick = onBackClick,
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    onBackClick: () -> Unit,
    uiState: DetailUiState
) {
    when (uiState) {
        is DetailUiState.Success -> {
            Scaffold(
                topBar = {
                    DetailToolbar(
                        onBackClick = onBackClick,
                        uiState = uiState
                    )
                },
                contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
                content = { contentPadding ->
                    LazyColumn(contentPadding = contentPadding) {
                        uiState.suggestion.forEach {
                            item {
                                SuggestionListItem(it)
                            }
                        }
                    }
                }
            )
        }

        DetailUiState.Error -> {
            Text(text = "Error")
        }

        DetailUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun SuggestionListItem(
    suggestion: String
) {
    Row {
        Text(
            text = suggestion,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
private fun DetailToolbar(
    onBackClick: () -> Unit,
    uiState: DetailUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.back_botton_content_desc,
                ),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        if (uiState is DetailUiState.Success) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = uiState.phrase.phrase
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        onBackClick = {},
        uiState = DetailUiState.Success(
            PhraseDomain(1, "This is my phrase", Date()),
            listOf(
                "Cest moi",
                "C'est encore moi",
                "Another one"
            )
        )
    )
}
