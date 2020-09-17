package jp.numero.android_dagashi.ui.milestone.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import jp.numero.android_dagashi.ui.UiState
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.repository.DagashiRepository
import jp.numero.android_dagashi.ui.toState
import kotlinx.coroutines.Dispatchers

class MilestoneDetailViewModel(
    private val milestone: Milestone,
    private val dagashiRepository: DagashiRepository
) : ViewModel() {
    val number = milestone.number

    val uiState: LiveData<UiState<MilestoneDetail>> = liveData(Dispatchers.IO) {
        emit(UiState(loading = true))
        val result = dagashiRepository.fetchMilestoneDetail(milestone.path)
        emit(result.toState())
    }
}

class MilestoneDetailViewModelFactory(
    private val milestone: Milestone,
    private val repository: DagashiRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilestoneDetailViewModel(milestone, repository) as T
    }
}