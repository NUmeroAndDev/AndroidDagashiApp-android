package jp.numero.android_dagashi.core.action

sealed class MilestoneDetailAction : Action {
    data class Fetch(val path: String) : MilestoneDetailAction()
}