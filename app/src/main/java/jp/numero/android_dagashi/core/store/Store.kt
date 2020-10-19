package jp.numero.android_dagashi.core.store

import androidx.lifecycle.ViewModel
import jp.numero.android_dagashi.core.action.Action

abstract class Store<A : Action> : ViewModel() {
    abstract fun dispatch(action: A)
}