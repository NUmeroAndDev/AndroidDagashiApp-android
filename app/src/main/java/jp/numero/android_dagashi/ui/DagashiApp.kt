package jp.numero.android_dagashi.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import jp.numero.android_dagashi.AppContainer
import jp.numero.android_dagashi.ui.milestone.MilestoneDetailScreen
import jp.numero.android_dagashi.ui.milestone.MilestoneListScreen
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun DagashiApp(
    appContainer: AppContainer
) {
    var screenState: Screen by remember { mutableStateOf(Screen.MilestoneList) }

    DagashiTheme {
        Crossfade(screenState) { screen ->
            Surface(color = MaterialTheme.colors.background) {
                when (screen) {
                    is Screen.MilestoneList -> MilestoneListScreen(onMilestoneSelected = {
                        screenState = Screen.MilestoneDetail(it)
                    }, repository = appContainer.dagashiRepository)
                    is Screen.MilestoneDetail -> MilestoneDetailScreen(
                        milestone = screen.milestone,
                        onBack = {
                            // FIXME back back navigation
                            screenState = Screen.MilestoneList
                        }
                    )
                }
            }
        }
    }
}