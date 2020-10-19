package jp.numero.android_dagashi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import jp.numero.android_dagashi.core.store.MilestoneDetailStore
import jp.numero.android_dagashi.core.store.MilestoneDetailStoreFactory
import jp.numero.android_dagashi.core.store.MilestonesStore
import jp.numero.android_dagashi.core.store.MilestonesStoreFactory
import jp.numero.android_dagashi.repository.DagashiRepository
import jp.numero.android_dagashi.ui.DagashiApp

class MainActivity : AppCompatActivity() {

    private val dagashiRepository: DagashiRepository
        get() = (application as DagashiApplication).appContainer.dagashiRepository

    private val milestonesStore: MilestonesStore by viewModels {
        MilestonesStoreFactory(
            dagashiRepository = dagashiRepository
        )
    }
    private val milestoneDetailStore: MilestoneDetailStore by viewModels {
        MilestoneDetailStoreFactory(
            dagashiRepository = dagashiRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DagashiApp(
                milestonesStore = milestonesStore,
                milestoneDetailStore = milestoneDetailStore
            )
        }
    }
}