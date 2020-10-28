package jp.numero.android_dagashi.ui

import jp.numero.android_dagashi.model.Milestone

sealed class Screen(val name: String) {
    object MilestoneList : Screen("milestoneList")

    object MilestoneDetail : Screen(
        "milestoneDetail/{${Argument.MilestonePath.key}}/{${Argument.MilestoneNumber.key}}"
    ) {
        fun route(milestone: Milestone): String {
            return "milestoneDetail/${milestone.path}/${milestone.number}"
        }

        enum class Argument(val key: String) {
            MilestonePath("MilestonePath"),
            MilestoneNumber("MilestoneNumber")
        }
    }
}