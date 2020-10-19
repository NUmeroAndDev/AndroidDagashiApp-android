package jp.numero.android_dagashi.core.reducer

import jp.numero.android_dagashi.core.action.MilestonesAction
import jp.numero.android_dagashi.core.state.MilestonesState
import jp.numero.android_dagashi.model.Result
import jp.numero.android_dagashi.repository.DagashiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MilestonesReducer(
    private val dagashiRepository: DagashiRepository,
) : Reducer<MilestonesState, MilestonesAction> {

    override suspend fun reduce(
        state: MilestonesState,
        action: MilestonesAction
    ): MilestonesState = withContext(Dispatchers.Default) {
        when (action) {
            is MilestonesAction.Fetch -> {
                when (val result = dagashiRepository.fetchMilestones()) {
                    is Result.Success -> {
                        state.copy(isLoading = false, milestones = result.value, error = null)
                    }
                    is Result.Failure -> {
                        state.copy(isLoading = false, error = result.exception)
                    }
                }
            }
        }
    }
}