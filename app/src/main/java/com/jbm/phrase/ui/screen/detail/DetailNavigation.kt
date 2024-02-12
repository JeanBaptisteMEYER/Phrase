package com.jbm.phrase.ui.screen.detail

import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jbm.phrase.navigation.URL_CHARACTER_ENCODING
import java.net.URLEncoder

const val DETAIL_ROUTE = "detail_route"

@VisibleForTesting
internal const val PHRASE_ID_ARG = "phraseId"

fun NavController.navigateToDetail(
    phraseId: String
) {
    val encodedId = URLEncoder.encode(phraseId, URL_CHARACTER_ENCODING)
    navigate("$DETAIL_ROUTE/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.detailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "$DETAIL_ROUTE/{$PHRASE_ID_ARG}",
        arguments = listOf(
            navArgument(PHRASE_ID_ARG) { type = NavType.StringType },
        ),
    ) { backStackEntry ->
        DetailDestination(
            onBackClick = onBackClick,
            phraseId = checkNotNull(backStackEntry.arguments?.getString(PHRASE_ID_ARG))
        )
    }
}
