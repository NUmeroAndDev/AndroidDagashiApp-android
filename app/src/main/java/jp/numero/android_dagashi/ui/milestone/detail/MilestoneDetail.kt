package jp.numero.android_dagashi.ui.milestone.detail

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.numero.android_dagashi.UiState
import jp.numero.android_dagashi.model.Issue
import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.ui.LoadingContent

@Composable
fun MilestoneDetailScreen(
    viewModel: MilestoneDetailViewModel,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.observeAsState(UiState()).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "#${viewModel.number}")
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
            LoadingContent(isLoading = uiState.loading) {
                val data = uiState.data
                if (data != null) {
                    MilestoneDetailContent(
                        milestoneDetail = data,
                        modifier = modifier
                    )
                } else {
                    // TODO error screen
                }
            }
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