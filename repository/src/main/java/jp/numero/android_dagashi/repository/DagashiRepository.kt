package jp.numero.android_dagashi.repository

import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.model.Milestones
import jp.numero.android_dagashi.model.Result

interface DagashiRepository {
    suspend fun fetchMilestones(): Result<Milestones>

    suspend fun fetchMilestoneDetail(path: String): Result<MilestoneDetail>
}