package jp.numero.android_dagashi.core.action

sealed class MilestonesAction : Action {
    object Fetch : MilestonesAction()
}