package jp.numero.android_dagashi.ui

import jp.numero.android_dagashi.model.Milestone

enum class ScreenName {
    MilestoneList, MilestoneDetail
}

sealed class Screen(val name: ScreenName) {
    object MilestoneList : Screen(ScreenName.MilestoneList)

    data class MilestoneDetail(
        val milestone: Milestone
    ) : Screen(ScreenName.MilestoneDetail)
}