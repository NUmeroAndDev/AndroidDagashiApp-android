package jp.numero.android_dagashi.repository

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import jp.numero.android_dagashi.data.DagashiApi
import jp.numero.android_dagashi.data.response.MilestoneDetailResponse
import jp.numero.android_dagashi.data.response.MilestonesResponse
import jp.numero.android_dagashi.model.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class DagashiRepositoryImpl: DagashiRepository {

    // FIXME Inject from Dagger
    private val dagashiApi: DagashiApi = createDagashiApi()

    override suspend fun fetchMilestones(): Milestones {
        return dagashiApi.getIndex().milestones.toModel()
    }

    override suspend fun fetchMilestoneDetail(path: String): MilestoneDetail {
        return dagashiApi.getIssue(path).toModel()
    }

    private fun MilestonesResponse.toModel(): Milestones {
        return Milestones(
            value = nodes.map { milestoneResponse ->
                Milestone(
                    id = milestoneResponse.id,
                    number = milestoneResponse.number,
                    description = milestoneResponse.description,
                    path = milestoneResponse.path
                )
            }
        )
    }

    private fun MilestoneDetailResponse.toModel(): MilestoneDetail {
        return MilestoneDetail(
            id = id,
            number = number,
            description = description,
            issues = issues.nodes.map { issueResponse ->
                Issue(
                    url = issueResponse.url,
                    title = issueResponse.title,
                    body = issueResponse.body,
                    labels = issueResponse.labels.nodes.map { labelResponse ->
                        Label(
                            name = labelResponse.name,
                            description = labelResponse.description,
                            color = labelResponse.color
                        )
                    },
                    comments = issueResponse.comments.nodes.map { commentResponse ->
                        val authorResponse = commentResponse.author
                        Comment(
                            body = commentResponse.body,
                            publishedAt = commentResponse.publishedAt,
                            author = Author(
                                id = authorResponse.login,
                                url = authorResponse.url,
                                icon = authorResponse.avatarUrl
                            )
                        )
                    }
                )
            }
        )
    }
}

fun createDagashiApi(): DagashiApi {
    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://androiddagashi.github.io")
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType))
        .build()

    return retrofit.create(DagashiApi::class.java)
}