package jp.numero.android_dagashi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import jp.numero.android_dagashi.ui.theme.DagashiTheme

@Composable
fun LoadingContent(
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        FullScreenIndicator()
    } else {
        content()
    }
}

@Composable
fun FullScreenIndicator() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview("FullScreenIndicator")
@Composable
fun FullScreenLoadingPreview() {
    DagashiTheme {
        FullScreenIndicator()
    }
}