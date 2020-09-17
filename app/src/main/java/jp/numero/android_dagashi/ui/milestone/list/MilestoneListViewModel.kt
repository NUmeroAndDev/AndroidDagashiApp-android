package jp.numero.android_dagashi.ui.milestone.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import jp.numero.android_dagashi.model.Milestones
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.Dispatchers

class MilestoneListViewModel(
    private val dagashiRepository: DagashiRepository
) : ViewModel() {
    val milestones: LiveData<Milestones> = liveData(Dispatchers.IO) {
        val response = dagashiRepository.fetchMilestones()
        if (response is Result.Success) {
            emit(response.value)
        }
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