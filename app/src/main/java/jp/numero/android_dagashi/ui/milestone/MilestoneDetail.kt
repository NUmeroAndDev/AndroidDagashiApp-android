package jp.numero.android_dagashi.ui.milestone

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.numero.android_dagashi.Greeting
import jp.numero.android_dagashi.model.Milestone

@Composable
fun MilestoneDetailScreen(
    milestone: Milestone,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = milestone.number.toString()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            Greeting(
                name = "MilestoneDetail"
            )
        }
    )
}