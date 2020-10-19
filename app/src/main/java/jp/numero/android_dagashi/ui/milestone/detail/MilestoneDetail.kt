package jp.numero.android_dagashi.ui.milestone.detail

import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import jp.numero.android_dagashi.model.*
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
    LaunchedTask {
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

        if (issue.comments.isNotEmpty()) {
            Spacer(modifier = Modifier.preferredHeight(12.dp))
            CommentsCard(comments = issue.comments)
        }
    }
}

@Composable
fun CommentsCard(comments: List<Comment>) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column {
            comments.forEach {
                CommentItem(comment = it)
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Row {
            CoilImage(
                request = ImageRequest.Builder(ContextAmbient.current)
                    .data(comment.author.icon)
                    .transformations(CircleCropTransformation())
                    .build(),
                modifier = Modifier.preferredSize(24.dp)
            )
            Spacer(modifier = Modifier.preferredWidth(8.dp))
            // TODO clickable author name
            Text(
                text = comment.author.id,
                modifier = Modifier.weight(1f)
                    .wrapContentWidth(align = Alignment.Start)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = comment.publishedAt,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.preferredHeight(8.dp))

        val uriHandler = UriHandlerAmbient.current
        val linkColor = MaterialTheme.colors.primary
        val linkedText = linkedTextFormatter(comment.body, linkColor)

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

@Preview("Comment Card")
@Composable
fun CommentCardPreview() {
    DagashiTheme {
        CommentsCard(
            comments = listOf(
                Comment(
                    body = "https://www.google.com/",
                    publishedAt = "2020/01/01 12:00:12",
                    author = Author(
                        id = "id",
                        url = "",
                        icon = ""
                    )
                )
            )
        )
    }
}