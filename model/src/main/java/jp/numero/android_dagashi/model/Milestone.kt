package jp.numero.android_dagashi.model

data class Milestone(
    val id: String,
    val number: Int,
    val description: String,
    val path: String
)

data class MilestoneDetail(
    val id: String,
    val number: Int,
    val description: String,
    val issues: List<Issue>
)