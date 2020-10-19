package jp.numero.android_dagashi.core.reducer

import jp.numero.android_dagashi.core.action.MilestoneDetailAction
import jp.numero.android_dagashi.core.state.MilestoneDetailState
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MilestoneDetailReducer(
    private val dagashiRepository: DagashiRepository,
) : Reducer<MilestoneDetailState, MilestoneDetailAction> {

    override suspend fun reduce(
        state: MilestoneDetailState,
        action: MilestoneDetailAction
    ): MilestoneDetailState = withContext(Dispatchers.Default) {
        when (action) {
            is MilestoneDetailAction.Fetch -> {
                when (val result = dagashiRepository.fetchMilestoneDetail(action.path)) {
                    is Result.Success -> {
                        state.copy(isLoading = false, milestoneDetail = result.value, error = null)
                    }
                    is Result.Failure -> {
                        state.copy(isLoading = false, error = result.exception)
                    }
                }
            }
        }
    }
}