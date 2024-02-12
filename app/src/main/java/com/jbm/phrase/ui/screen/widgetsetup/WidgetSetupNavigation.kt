package com.jbm.phrase.ui.screen.widgetsetup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val WIDGET_SETUP_GRAPH_ROUTE = "widget_setup_graph_route"
const val WIDGET_SETUP_ROUTE = "widget_setup_route"

fun NavController.navigateToWidgetSetup(navOptions: NavOptions? = null) =
    navigate(WIDGET_SETUP_ROUTE, navOptions)

fun NavGraphBuilder.widgetSetupGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = WIDGET_SETUP_GRAPH_ROUTE,
        startDestination = WIDGET_SETUP_ROUTE,
    ) {
        composable(route = WIDGET_SETUP_ROUTE) {
            WidgetSetupDestination(appWidgetId = -1)
        }
        nestedGraphs()
    }
}
