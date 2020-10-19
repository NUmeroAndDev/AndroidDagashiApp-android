package jp.numero.android_dagashi.core.state

import jp.numero.android_dagashi.model.Milestones

data class MilestonesState(
    val isLoading: Boolean = false,
    val milestones: Milestones? = null,
    val error: Throwable? = null
) : State