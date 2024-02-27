package com.jbm.phrase.ui.screen.widgetsetup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jbm.module.core.data.model.PhraseDomain
import com.jbm.phrase.R

@Composable
fun WidgetSetupDestination(
    onConfigurationFinished: (Int, Int) -> Unit = { _, _ -> },
    appWidgetId: Int,
    viewModel: WidgetSetupViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val fontProvider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    WidgetSetupScreen(
        onConfigurationFinished = onConfigurationFinished,
        onPhraseItemClicked = viewModel::setupNewWidget,
        onFontItemClicked = viewModel::onTextFontClicked,
        onDecreaseTextSizeClicked = viewModel::onDecreaseTextSizeClicked,
        onIncreaseTextSizeClicked = viewModel::onIncreaseTextSizeClicked,
        onTextColorItemClicked = viewModel::onTextColorClicked,
        onBackgroundColorClicked = viewModel::onBackgroundColorClicked,
        appWidgetId = appWidgetId,
        uiState = uiState,
        fontProvider = fontProvider
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.loadData()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetSetupScreen(
    onConfigurationFinished: (Int, Int) -> Unit = { _, _ -> },
    onPhraseItemClicked: (Int, String, Int, Int) -> Unit,
    onFontItemClicked: () -> Unit = {},
    onDecreaseTextSizeClicked: () -> Unit = {},
    onIncreaseTextSizeClicked: () -> Unit = {},
    onTextColorItemClicked: () -> Unit = {},
    onBackgroundColorClicked: () -> Unit = {},
    appWidgetId: Int,
    uiState: WidgetSetupUiState,
    fontProvider: GoogleFont.Provider
) {
    when (uiState) {
        is WidgetSetupUiState.Loading -> {
            Text(text = "Loading...")
        }

        is WidgetSetupUiState.Error -> {
            Text(text = "Error")
        }

        is WidgetSetupUiState.WidgetSetup -> {
            Scaffold(
                bottomBar = {
                    WidgetSetupBottomSheetView(
                        onFontItemClicked = onFontItemClicked,
                        onDecreaseTextSizeClicked = onDecreaseTextSizeClicked,
                        onIncreaseTextSizeClicked = onIncreaseTextSizeClicked,
                        onTextColorItemClicked = onTextColorItemClicked,
                        onBackgroundColorClicked = onBackgroundColorClicked,
                        uiState = uiState,
                        fontProvider = fontProvider
                    )
                }
            ) { paddingValue ->
                WidgetSetupListView(
                    paddingValues = paddingValue,
                    onPhraseItemClicked = onPhraseItemClicked,
                    uiState = uiState,
                    fontProvider = fontProvider
                )
            }
        }

        is WidgetSetupUiState.WidgetSetupSuccess -> {
            onConfigurationFinished(
                appWidgetId,
                uiState.widgetId
            )
        }
    }
}

@Composable
fun WidgetSetupListView(
    paddingValues: PaddingValues,
    onPhraseItemClicked: (Int, String, Int, Int) -> Unit,
    uiState: WidgetSetupUiState.WidgetSetup,
    fontProvider: GoogleFont.Provider
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
    ) {
        itemsIndexed(uiState.phrases) { index, phrase ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onPhraseItemClicked(
                            phrase.id,
                            uiState.textFontName,
                            uiState.textSize,
                            uiState.textColor
                        )
                    }
            ) {
                Text(
                    text = phrase.phrase,
                    fontSize = uiState.textSize.sp,
                    color = Color(uiState.textColor),
                    fontFamily = FontFamily(
                        Font(
                            googleFont = GoogleFont(uiState.textFontName),
                            fontProvider = fontProvider
                        )
                    )
                )
            }
            if (index < uiState.phrases.lastIndex) {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun WidgetSetupBottomSheetView(
    onFontItemClicked: () -> Unit = {},
    onDecreaseTextSizeClicked: () -> Unit = {},
    onIncreaseTextSizeClicked: () -> Unit = {},
    onTextColorItemClicked: () -> Unit = {},
    onBackgroundColorClicked: () -> Unit = {},
    uiState: WidgetSetupUiState.WidgetSetup,
    fontProvider: GoogleFont.Provider
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.widget_configuration_footer_title),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        FontItem(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            onFontItemClicked = onFontItemClicked,
            uiState = uiState,
            fontProvider = fontProvider
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TextSizeItem(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            onDecreaseTextSizeClicked = onDecreaseTextSizeClicked,
            onIncreaseTextSizeClicked = onIncreaseTextSizeClicked,
            uiState = uiState
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ColorPickerItem(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            onTextColorItemClicked = onTextColorItemClicked,
            title = stringResource(R.string.widget_configuration_text_color),
            uiState = uiState
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ColorPickerItem(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            onTextColorItemClicked = onBackgroundColorClicked,
            title = stringResource(R.string.widget_configuration_background_color),
            uiState = uiState
        )
    }
}

@Composable
fun FontItem(
    modifier: Modifier = Modifier,
    onFontItemClicked: () -> Unit = {},
    uiState: WidgetSetupUiState.WidgetSetup,
    fontProvider: GoogleFont.Provider
) {
    Row(
        modifier = modifier
            .clickable { onFontItemClicked() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.widget_configuration_font))
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.textFontName,
                fontFamily = FontFamily(
                    Font(
                        googleFont = GoogleFont(uiState.textFontName),
                        fontProvider = fontProvider
                    ),
                    androidx.compose.ui.text.font.Font(R.font.roboto_regular)
                )
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun TextSizeItem(
    modifier: Modifier = Modifier,
    onDecreaseTextSizeClicked: () -> Unit = {},
    onIncreaseTextSizeClicked: () -> Unit = {},
    uiState: WidgetSetupUiState.WidgetSetup
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.widget_configuration_text_size))
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = stringResource(R.string.widget_configuration_text_size_decrease_content_desc),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { onDecreaseTextSizeClicked() }
            )
            Text(
                text = uiState.textSize.toString(),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = stringResource(R.string.widget_configuration_text_size_increase_content_desc),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { onIncreaseTextSizeClicked() }
            )
        }
    }
}

@Composable
fun ColorPickerItem(
    modifier: Modifier = Modifier,
    onTextColorItemClicked: () -> Unit = {},
    title: String,
    uiState: WidgetSetupUiState.WidgetSetup
) {
    Row(
        modifier = modifier
            .clickable { onTextColorItemClicked() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_round),
                contentDescription = stringResource(R.string.widget_configuration_text_color_content_desc),
                tint = Color(uiState.textColor),
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = MaterialTheme.shapes.large
                    )
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = stringResource(R.string.widget_configuration_text_color_content_desc),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WidgetSetupScreenPreview() {

    val fontProvider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    WidgetSetupScreen(
        onPhraseItemClicked = { _, _, _, _ -> },
        uiState = WidgetSetupUiState.WidgetSetup(
            listOf(
                PhraseDomain(
                    1,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                ),
                PhraseDomain(
                    2,
                    "Donec at dui vitae nulla congue vehicula ut sit amet enim."
                ),
                PhraseDomain(
                    3,
                    "Proin pellentesque magna eget augue maximus egestas."
                ),
                PhraseDomain(
                    4,
                    "Pellentesque vitae enim ut risus blandit convallis."
                ),
                PhraseDomain(
                    5,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                ),
                PhraseDomain(
                    6,
                    "Donec at dui vitae nulla congue vehicula ut sit amet enim."
                ),
                PhraseDomain(
                    7,
                    "Proin pellentesque magna eget augue maximus egestas."
                ),
                PhraseDomain(
                    8,
                    "Pellentesque vitae enim ut risus blandit convallis."
                )
            )
        ),
        appWidgetId = 1,
        fontProvider = fontProvider
    )
}
