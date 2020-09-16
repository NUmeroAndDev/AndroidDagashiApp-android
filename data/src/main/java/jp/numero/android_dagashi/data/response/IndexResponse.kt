package jp.numero.android_dagashi.data.response

import kotlinx.serialization.Serializable

@Serializable
data class IndexResponse(
    val milestones: MilestonesResponse,
)

@Serializable
data class MilestonesResponse(
    val nodes: List<MilestoneResponse>,
)

@Serializable
data class MilestoneResponse(
    val id: String,
    val number: Int,
    val description: String,
    val path: String,
    val closedAt: String
)