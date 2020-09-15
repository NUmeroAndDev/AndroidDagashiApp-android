package jp.numero.android_dagashi.repository

import jp.numero.android_dagashi.model.MilestoneDetail
import jp.numero.android_dagashi.model.Milestones

interface DagashiRepository {
    suspend fun fetchMilestones(): Milestones

    suspend fun fetchMilestoneDetail(path: String): MilestoneDetail
}