package jp.numero.android_dagashi.ui.milestone

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.Milestones
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository

@Composable
fun MilestoneListScreen(
    onMilestoneSelected: (Milestone) -> Unit,
    repository: DagashiRepository
) {
    val milestones = remember { mutableStateOf(Milestones(emptyList())) }
    launchInComposition(repository, Unit) {
        val result = repository.fetchMilestones()
        if (result is Result.Success) {
            milestones.value = result.value
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "AndroidDagashi") }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            MileStoneListContent(
                milestones = milestones.value,
                modifier = modifier,
                navigateTo = onMilestoneSelected
            )
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
    Row(
        modifier = Modifier.clickable(onClick = { navigateTo(milestone) }).padding(16.dp)
    ) {
        Text(
            text = milestone.description,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .preferredHeightIn(minHeight = 56.dp)
                .wrapContentHeight()
        )
    }
}