package jp.numero.android_dagashi.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import jp.numero.android_dagashi.core.store.MilestoneDetailStore
import jp.numero.android_dagashi.core.store.MilestonesStore
import jp.numero.android_dagashi.ui.milestone.detail.MilestoneDetailScreen
import jp.numero.android_dagashi.ui.milestone.list.MilestoneListScreen
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun DagashiApp(
    milestonesStore: MilestonesStore,
    milestoneDetailStore: MilestoneDetailStore,
) {
    var screenState: Screen by remember { mutableStateOf(Screen.MilestoneList) }

    DagashiTheme {
        Crossfade(screenState) { screen ->
            Surface(color = MaterialTheme.colors.background) {
                when (screen) {
                    is Screen.MilestoneList -> {
                        MilestoneListScreen(
                            onMilestoneSelected = {
                                screenState = Screen.MilestoneDetail(it)
                            },
                            milestonesStore = milestonesStore
                        )
                    }
                    is Screen.MilestoneDetail -> {
                        MilestoneDetailScreen(
                            onBack = {
                                // FIXME back back navigation
                                screenState = Screen.MilestoneList
                            },
                            milestone = screen.milestone,
                            milestoneDetailStore = milestoneDetailStore
                        )
                    }
                }
            }
        }
    }
}