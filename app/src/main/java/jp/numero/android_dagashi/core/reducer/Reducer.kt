package jp.numero.android_dagashi.core.reducer

import jp.numero.android_dagashi.core.action.Action
import jp.numero.android_dagashi.core.state.State

interface Reducer<S : State, A: Action> {
    suspend fun reduce(state: S, action: A): S
}