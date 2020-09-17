package jp.numero.android_dagashi.ui.milestone.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import jp.numero.android_dagashi.model.Milestone
import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.Dispatchers

class MilestoneDetailViewModel(
    private val milestone: Milestone,
    private val dagashiRepository: DagashiRepository
) : ViewModel() {
    val number = milestone.number

    val milestoneDetail: LiveData<MilestoneDetail> = liveData(Dispatchers.IO) {
        val response = dagashiRepository.fetchMilestoneDetail(milestone.path)
        if (response is Result.Success) {
            emit(response.value)
        }
    }
}

class MilestoneDetailViewModelFactory(
    private val milestone: Milestone,
    private val repository: DagashiRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilestoneDetailViewModel(milestone, repository) as T
    }
}