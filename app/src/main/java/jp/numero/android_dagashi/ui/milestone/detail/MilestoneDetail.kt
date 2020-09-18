package jp.numero.android_dagashi.ui.milestone.detail

import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import jp.numero.android_dagashi.model.Issue
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.repository.DagashiRepository
import jp.numero.android_dagashi.ui.LoadingContent
import jp.numero.android_dagashi.ui.UiState
import jp.numero.android_dagashi.ui.linkedTextFormatter
import jp.numero.android_dagashi.ui.theme.DagashiTheme
import jp.numero.android_dagashi.ui.toState

@Composable
fun MilestoneDetailScreen(
    milestone: Milestone,
    dagashiRepository: DagashiRepository,
    onBack: () -> Unit
) {
    val uiState = remember { mutableStateOf(UiState<MilestoneDetail>()) }
    launchInComposition {
        uiState.value = dagashiRepository.fetchMilestoneDetail(milestone.path).toState()
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
            val state = uiState.value
            LoadingContent(isLoading = state.loading) {
                val data = state.data
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
    LazyColumnForIndexed(
        items = milestoneDetail.issues,
        modifier = modifier
    ) { index, issue ->
        if (index > 0) {
            IssueListDivider()
        }
        IssueItem(issue = issue)
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

        val uriHandler = UriHandlerAmbient.current
        val linkColor = MaterialTheme.colors.primary
        val linkedText = linkedTextFormatter(issue.body, linkColor)

        ClickableText(
            text = linkedText,
            style = MaterialTheme.typography.body2,
            onClick = {
                linkedText
                    .getStringAnnotations(
                        start = it,
                        end = it
                    )
                    .firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
            }
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

@Preview("Issue Item")
@Composable
fun IssueItemPreview() {
    DagashiTheme {
        IssueItem(
            issue = Issue(
                url = "",
                title = "Title",
                body = "https://www.google.com/",
                labels = emptyList(),
                comments = emptyList()
            )
        )
    }
}