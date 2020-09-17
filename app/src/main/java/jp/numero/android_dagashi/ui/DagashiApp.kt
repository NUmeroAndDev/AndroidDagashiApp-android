package jp.numero.android_dagashi.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import jp.numero.android_dagashi.AppContainer
import jp.numero.android_dagashi.ui.milestone.detail.MilestoneDetailScreen
import jp.numero.android_dagashi.ui.milestone.list.MilestoneListScreen
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun DagashiApp(
    appContainer: AppContainer
) {
    var screenState: Screen by remember { mutableStateOf(Screen.MilestoneList) }

    val repository = appContainer.dagashiRepository

    DagashiTheme {
        Crossfade(screenState) { screen ->
            Surface(color = MaterialTheme.colors.background) {
                when (screen) {
                    is Screen.MilestoneList -> {
                        MilestoneListScreen(
                            onMilestoneSelected = {
                                screenState = Screen.MilestoneDetail(it)
                            },
                            dagashiRepository = repository
                        )
                    }
                    is Screen.MilestoneDetail -> {
                        MilestoneDetailScreen(
                            onBack = {
                                // FIXME back back navigation
                                screenState = Screen.MilestoneList
                            },
                            milestone = screen.milestone,
                            dagashiRepository = repository
                        )
                    }
                }
            }
        }
    }
}