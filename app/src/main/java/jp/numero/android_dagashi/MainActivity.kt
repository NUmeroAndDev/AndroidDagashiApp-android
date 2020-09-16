package jp.numero.android_dagashi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import jp.numero.android_dagashi.ui.DagashiApp
import jp.numero.android_dagashi.ui.theme.DagashiTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as DagashiApplication).appContainer
        setContent {
            DagashiApp(
                appContainer = appContainer
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DagashiTheme {
        Greeting("Android")
    }
}