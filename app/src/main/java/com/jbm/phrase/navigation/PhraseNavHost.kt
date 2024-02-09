package com.jbm.phrase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jbm.phrase.ui.screen.detail.detailScreen
import com.jbm.phrase.ui.screen.detail.navigateToDetail
import com.jbm.phrase.ui.screen.home.HOME_ROUTE
import com.jbm.phrase.ui.screen.home.homeScreen
import com.jbm.phrase.ui.screen.widgetsetup.selectPhraseScreen

@Composable
fun PhraseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            onDetailClick = navController::navigateToDetail
        )
        detailScreen(
            onBackClick = navController::popBackStack
        )
        selectPhraseScreen()
    }
}
