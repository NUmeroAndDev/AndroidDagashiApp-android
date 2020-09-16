package jp.numero.android_dagashi.model

data class Milestones(
    val value: List<Milestone>
)

data class Milestone(
    val id: String,
    val number: Int,
    val description: String,
    val path: String,
    val closedAd: String
)

data class MilestoneDetail(
    val id: String,
    val number: Int,
    val description: String,
    val issues: List<Issue>
) {
    companion object {
        val empty = MilestoneDetail(
            id = "",
            number = 0,
            description = "",
            issues = emptyList()
        )
    }
}