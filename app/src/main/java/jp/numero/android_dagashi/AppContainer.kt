package jp.numero.android_dagashi

import jp.numero.android_dagashi.repository.DagashiRepository
import jp.numero.android_dagashi.repository.DagashiRepositoryImpl

interface AppContainer {
    val dagashiRepository: DagashiRepository
}

class AppContainerImpl : AppContainer {
    override val dagashiRepository: DagashiRepository by lazy {
        DagashiRepositoryImpl()
    }
}