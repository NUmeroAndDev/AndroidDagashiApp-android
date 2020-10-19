package jp.numero.android_dagashi.core.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.numero.android_dagashi.core.action.MilestonesAction
import jp.numero.android_dagashi.core.reducer.MilestonesReducer
import jp.numero.android_dagashi.core.state.MilestonesState
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MilestonesStore(
    private val reducer: MilestonesReducer
): Store<MilestonesAction>() {

    private val _state = MutableStateFlow(MilestonesState(isLoading = true))
    var state: StateFlow<MilestonesState> = _state
    private set

    override fun dispatch(action: MilestonesAction) {
        viewModelScope.launch {
            _state.value = reducer.reduce(state.value, action)
        }
    }
}

class MilestonesStoreFactory(
    private val dagashiRepository: DagashiRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilestonesStore(
            MilestonesReducer(dagashiRepository = dagashiRepository)
        ) as T
    }
}