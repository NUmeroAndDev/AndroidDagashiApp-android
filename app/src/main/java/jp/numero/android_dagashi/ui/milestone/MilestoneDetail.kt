package jp.numero.android_dagashi.ui.milestone

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.numero.android_dagashi.model.Issue
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository

@Composable
fun MilestoneDetailScreen(
    milestone: Milestone,
    onBack: () -> Unit,
    repository: DagashiRepository
) {
    val milestoneDetail = remember { mutableStateOf(MilestoneDetail.empty) }
    launchInComposition(repository, Unit) {
        val result = repository.fetchMilestoneDetail(milestone.path)
        if (result is Result.Success) {
            milestoneDetail.value = result.value
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "#${milestone.number}")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            MilestoneDetailContent(
                milestoneDetail = milestoneDetail.value,
                modifier = modifier
            )
        }
    )
}

@Composable
fun MilestoneDetailContent(
    milestoneDetail: MilestoneDetail,
    modifier: Modifier = Modifier
) {
    ScrollableColumn(
        modifier = modifier
    ) {
        milestoneDetail.issues.forEachIndexed { index, issue ->
            if (index > 0) {
                IssueListDivider()
            }
            IssueItem(issue = issue)
        }
    }
}

@Composable
fun IssueItem(
    issue: Issue
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = issue.title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.preferredHeight(4.dp))
        Text(
            text = issue.body,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.54f)
        )
    }
}

@Composable
private fun IssueListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}