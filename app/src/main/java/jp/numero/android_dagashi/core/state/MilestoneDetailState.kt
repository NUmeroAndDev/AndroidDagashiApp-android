package jp.numero.android_dagashi.core.state

import jp.numero.android_dagashi.model.MilestoneDetail

data class MilestoneDetailState(
    val isLoading: Boolean = false,
    val milestoneDetail: MilestoneDetail? = null,
    val error: Throwable? = null
) : State