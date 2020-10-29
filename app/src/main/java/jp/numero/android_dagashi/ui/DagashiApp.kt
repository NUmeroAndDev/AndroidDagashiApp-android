package jp.numero.android_dagashi.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import jp.numero.android_dagashi.AppContainer
import jp.numero.android_dagashi.ui.milestone.detail.MilestoneDetailScreen
import jp.numero.android_dagashi.ui.milestone.list.MilestoneListScreen
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun DagashiApp(
    appContainer: AppContainer
) {
    val navController = rememberNavController()
    val repository = appContainer.dagashiRepository

    DagashiTheme {
        NavHost(navController, startDestination = Screen.MilestoneList.name) {
            composable(Screen.MilestoneList.name) {
                MilestoneListScreen(
                    dagashiRepository = repository,
                    navController = navController
                )
            }
            composable(
                route = Screen.MilestoneDetail.name,
                arguments = listOf(
                    navArgument(Screen.MilestoneDetail.Argument.MilestonePath.key) {
                        type = NavType.StringType
                    },
                    navArgument(Screen.MilestoneDetail.Argument.MilestoneNumber.key) {
                        type = NavType.IntType
                    }
                )
            ) {
                MilestoneDetailScreen(
                    milestonePath = checkNotNull(it.arguments?.getString(Screen.MilestoneDetail.Argument.MilestonePath.key)),
                    milestoneNumber = checkNotNull(it.arguments?.getInt(Screen.MilestoneDetail.Argument.MilestoneNumber.key)),
                    dagashiRepository = repository,
                    navController = navController
                )
            }
        }
    }
}