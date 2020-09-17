package jp.numero.android_dagashi.ui.milestone.list

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import jp.numero.android_dagashi.R
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.Milestones
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun MilestoneListScreen(
    viewModel: MilestoneListViewModel,
    onMilestoneSelected: (Milestone) -> Unit
) {
    val milestones = viewModel.milestones.observeAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val context = ContextAmbient.current
                    Text(text = context.getString(R.string.app_name))
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            if (milestones != null) {
                MileStoneListContent(
                    milestones = milestones,
                    modifier = modifier,
                    navigateTo = onMilestoneSelected
                )
            }
        }
    )
}

@Composable
fun MileStoneListContent(
    milestones: Milestones,
    modifier: Modifier = Modifier,
    navigateTo: (Milestone) -> Unit
) {
    LazyColumnFor(
        milestones.value,
        modifier = modifier
    ) { milestone ->
        MilestoneItem(milestone = milestone, navigateTo = navigateTo)
    }
}

@Composable
fun MilestoneItem(
    milestone: Milestone,
    navigateTo: (Milestone) -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = { navigateTo(milestone) })
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "#${milestone.number}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = milestone.closedAd,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.preferredHeight(4.dp))
        Text(
            text = milestone.description,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.54f)
        )
    }
}

@Preview("Milestone Item")
@Composable
fun MilestoneItemPreview() {
    DagashiTheme {
        MilestoneItem(
            milestone = Milestone(
                id = "id",
                number = 100,
                description = "Description",
                path = "",
                closedAd = "2020-09-13"
            ),
            navigateTo = {}
        )
    }
}