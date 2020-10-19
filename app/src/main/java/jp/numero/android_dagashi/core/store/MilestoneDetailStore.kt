package jp.numero.android_dagashi.core.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.numero.android_dagashi.core.action.MilestoneDetailAction
import jp.numero.android_dagashi.core.reducer.MilestoneDetailReducer
import jp.numero.android_dagashi.core.state.MilestoneDetailState
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MilestoneDetailStore(
    private val reducer: MilestoneDetailReducer
): Store<MilestoneDetailAction>() {

    private val _state = MutableStateFlow(MilestoneDetailState(isLoading = true))
    var state: StateFlow<MilestoneDetailState> = _state
    private set

    override fun dispatch(action: MilestoneDetailAction) {
        viewModelScope.launch {
            _state.value = reducer.reduce(state.value, action)
        }
    }
}

class MilestoneDetailStoreFactory(
    private val dagashiRepository: DagashiRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilestoneDetailStore(
            MilestoneDetailReducer(dagashiRepository = dagashiRepository)
        ) as T
    }
}