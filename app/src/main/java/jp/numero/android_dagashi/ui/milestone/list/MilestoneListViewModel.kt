package jp.numero.android_dagashi.ui.milestone.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import jp.numero.android_dagashi.UiState
import jp.numero.android_dagashi.model.Milestones
import jp.numero.android_dagashi.repository.DagashiRepository
import jp.numero.android_dagashi.toState
import kotlinx.coroutines.Dispatchers

class MilestoneListViewModel(
    private val dagashiRepository: DagashiRepository
) : ViewModel() {
    val uiState: LiveData<UiState<Milestones>> = liveData(Dispatchers.IO) {
        emit(UiState(loading = true))
        val result = dagashiRepository.fetchMilestones()
        emit(result.toState())
    }
}

class MilestoneListViewModelFactory(
    private val repository: DagashiRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilestoneListViewModel(repository) as T
    }
}