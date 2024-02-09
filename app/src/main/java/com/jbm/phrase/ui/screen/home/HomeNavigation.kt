package com.jbm.phrase.ui.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onDetailClick: (String) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeDestination(
            onDetailClick = onDetailClick
        )
    }
}
