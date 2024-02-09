package com.jbm.phrase.ui.screen.widgetsetup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SELECT_PHRASE_ROUTE = "select_phrase_route"

fun NavController.navigateToSelectPhrase(navOptions: NavOptions? = null) =
    navigate(SELECT_PHRASE_ROUTE, navOptions)

fun NavGraphBuilder.selectPhraseScreen(
) {
    composable(route = SELECT_PHRASE_ROUTE) {
        SelectPhraseDestination()
    }
}
