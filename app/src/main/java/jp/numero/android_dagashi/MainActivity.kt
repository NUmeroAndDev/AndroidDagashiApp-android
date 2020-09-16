package jp.numero.android_dagashi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import jp.numero.android_dagashi.ui.DagashiApp

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